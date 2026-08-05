import uuid
from datetime import datetime
from enum import Enum
from app.extensions import db

# ENUMS
class SpotStatus(str, Enum):
    AVAILABLE = "AVAILABLE"
    RESERVED = "RESERVED"
    OCCUPIED = "OCCUPIED"
    UNDER_MAINTENANCE = "UNDER_MAINTENANCE"

class VehicleType(str, Enum):
    CAR = "CAR"
    BIKE = "BIKE"
    VAN = "VAN"
    THREE_WHEEL = "THREE_WHEEL"

# MODELS
class ParkingSpot(db.Model):
    __tablename__ = 'parking_spots'

    spot_id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid.uuid4()))
    location_name = db.Column(db.String(255), nullable=False)
    allowed_vehicle_type = db.Column(db.String(50), nullable=False)
    price_per_hour = db.Column(db.Float, nullable=False)
    status = db.Column(db.String(50), default=SpotStatus.AVAILABLE.value)
    owner_id = db.Column(db.String(100), nullable=False)

class Booking(db.Model):
    __tablename__ = 'bookings'

    booking_id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid.uuid4()))
    user_id = db.Column(db.String(100), nullable=False)
    spot_id = db.Column(db.String(36), nullable=False)
    vehicle_id = db.Column(db.String(100), nullable=False)
    start_time = db.Column(db.DateTime, default=datetime.utcnow)
    end_time = db.Column(db.DateTime, nullable=True)
    booking_status = db.Column(db.String(50), default="CONFIRMED")