from flask import Flask
import py_eureka_client.eureka_client as eureka_client
from app.config import Config
from app.extensions import db
from app.controllers.parking_spot_controller import parking_bp

def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)

    # Database Initialization
    db.init_app(app)

    # Register Blueprints (Controllers)
    app.register_blueprint(parking_bp)

    # Eureka Registration
    try:
        eureka_client.init(
            eureka_server=Config.EUREKA_SERVER,
            app_name="parking-spot-service",
            instance_port=8087,
            instance_host="localhost"
        )
    except Exception as e:
        print(f"Eureka init warning: {e}")

    # Create Tables
    with app.app_context():
        db.create_all()

    return app