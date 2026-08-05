import uuid
from datetime import datetime
from flask import Blueprint, request, jsonify
from app.extensions import db
from app.models.parking_spot import ParkingSpot, Booking, SpotStatus

parking_bp = Blueprint('parking', __name__, url_prefix='/api/v1/parking')

# 0. Spot එකක් add කිරීම
@parking_bp.route('/spots', methods=['POST'])
def add_spot():
    try:
        data = request.get_json()

        new_spot = ParkingSpot(
            spot_id=str(uuid.uuid4()),
            location_name=data['locationName'],
            allowed_vehicle_type=data['allowedVehicleType'],
            price_per_hour=float(data['pricePerHour']),
            status=SpotStatus.AVAILABLE.value,
            owner_id=data['ownerId']
        )

        db.session.add(new_spot)
        db.session.commit()

        return jsonify({
            "spotId": new_spot.spot_id,
            "locationName": new_spot.location_name,
            "allowedVehicleType": new_spot.allowed_vehicle_type,
            "pricePerHour": new_spot.price_per_hour,
            "status": new_spot.status,
            "ownerId": new_spot.owner_id
        }), 201

    except Exception as e:
        db.session.rollback()
        return jsonify({"error": str(e)}), 400


# 1. Search / Filter Spots
@parking_bp.route('/spots/search', methods=['GET'])
def search_spots():
    try:
        location = request.args.get('location')
        vehicle_type = request.args.get('vehicleType')

        query = ParkingSpot.query.filter_by(status=SpotStatus.AVAILABLE.value)

        if location:
            query = query.filter(ParkingSpot.location_name.ilike(f"%{location}%"))
        if vehicle_type:
            query = query.filter_by(allowed_vehicle_type=vehicle_type)

        spots = query.all()

        result = [
            {
                "spotId": s.spot_id,
                "locationName": s.location_name,
                "allowedVehicleType": s.allowed_vehicle_type,
                "pricePerHour": s.price_per_hour,
                "status": s.status,
                "ownerId": s.owner_id
            } for s in spots
        ]

        return jsonify(result), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# 2. Reserve Parking Spot
@parking_bp.route('/reserve', methods=['POST'])
def reserve_spot():
    try:
        user_id = request.args.get('userId')
        spot_id = request.args.get('spotId')
        vehicle_id = request.args.get('vehicleId')

        spot = ParkingSpot.query.get(spot_id)
        if not spot:
            return jsonify({"error": "Parking spot not found!"}), 404

        if spot.status != SpotStatus.AVAILABLE.value:
            return jsonify({"error": "Spot is not available for booking!"}), 400

        spot.status = SpotStatus.RESERVED.value

        booking = Booking(
            booking_id=str(uuid.uuid4()),
            user_id=user_id,
            spot_id=spot_id,
            vehicle_id=vehicle_id,
            start_time=datetime.utcnow(),
            booking_status="CONFIRMED"
        )

        db.session.add(booking)
        db.session.commit()

        return jsonify({
            "bookingId": booking.booking_id,
            "userId": booking.user_id,
            "spotId": booking.spot_id,
            "vehicleId": booking.vehicle_id,
            "startTime": booking.start_time.isoformat(),
            "endTime": booking.end_time,
            "bookingStatus": booking.booking_status
        }), 200

    except Exception as e:
        db.session.rollback()
        return jsonify({"error": str(e)}), 400


# 3. Release Parking Spot
@parking_bp.route('/spots/<spot_id>/release', methods=['POST'])
def release_spot(spot_id):
    try:
        spot = ParkingSpot.query.get(spot_id)
        if not spot:
            return jsonify({"error": "Parking spot not found!"}), 404

        spot.status = SpotStatus.AVAILABLE.value

        booking = Booking.query.filter_by(spot_id=spot_id, booking_status="CONFIRMED").first()
        if not booking:
            return jsonify({"error": "No active booking found for this spot!"}), 404

        booking.end_time = datetime.utcnow()
        booking.booking_status = "RELEASED"

        db.session.commit()

        return jsonify({
            "bookingId": booking.booking_id,
            "userId": booking.user_id,
            "spotId": booking.spot_id,
            "vehicleId": booking.vehicle_id,
            "startTime": booking.start_time.isoformat(),
            "endTime": booking.end_time.isoformat(),
            "bookingStatus": booking.booking_status
        }), 200

    except Exception as e:
        db.session.rollback()
        return jsonify({"error": str(e)}), 400