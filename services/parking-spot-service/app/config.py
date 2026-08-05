import os

class Config:
    SQLALCHEMY_DATABASE_URI = 'mysql+pymysql://savindu:gsavindu@localhost:3306/parking_space_db'
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    EUREKA_SERVER = "http://localhost:8761/eureka/"