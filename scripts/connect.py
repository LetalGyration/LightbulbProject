from os import error
import psycopg2
from requests import exceptions
from requests import codes
from config import config
import requests
import json

host_url = "http://localhost:8080/api/"
pg_query = None
response_code = None
response_result = None
response_value = None
expected_value = None
body = None

def connect():
    """ Connect to the PostgreSQL database server """
    #read parameters of connection
    params = config()

    #connection to server
    print('Connecting to the PostgreSQL database')
    conn = psycopg2.connect(**params)
    return conn

def closeConnection(connection):
    connection.close()
    print('\nDatabase connection closed')

def getAllRoomsFromDb(connection):
    try:

        print('Room list in database:')
        pg_query = """ SELECT * FROM room """
        curs = connection.cursor()
        curs.execute(pg_query)
        db_list = curs.fetchall()
        print(db_list)

    except (Exception, psycopg2.DatabaseError) as error:
        print(error)

def getRoomById(connection, room_id):
    try:
        pg_query = f""" SELECT * FROM room WHERE room_id = {room_id}"""
        curs = connection.cursor()
        curs.execute(pg_query)
        room = curs.fetchall()
    except (Exception, psycopg2.DatabaseError) as error:
        print(error)
    return room

def getAddedRoomId(connection):
    try:
        pg_query = """ SELECT room_id FROM room WHERE room_name = %s """
        record = ('PyRoom',)
        curs = connection.cursor()
        curs.execute(pg_query, record)
        room_id = curs.fetchone()
    except (Exception, psycopg2.DatabaseError) as error:
        print(error)
    return room_id

def addRoomToDb(connection):
    try:  
        print('\nAdding new room in table')
        pg_query = """ INSERT INTO room (room_name, country_name, status) VALUES (%s, %s, %s) """
        record = ('PyRoom', 'Belarus', False)
        curs = connection.cursor()
        curs.execute(pg_query, record)
        connection.commit()
    except (Exception, psycopg2.DatabaseError) as error:
        print(error)

def deleteNewRoom(connection):
    try:
        print('\nDeleting new rooms from table')
        pg_query = """ DELETE FROM room WHERE room_name = %s """
        record = ('PyRoom',)
        curs = connection.cursor()
        curs.execute(pg_query, record)
        connection.commit()
    except (Exception, psycopg2.DatabaseError) as error:
        print(error)


#GET /api/rooms
def getRoomsTest():
    print('\n*************')
    print('\nTest get rooms')
    expected_value = f"[{{'id': 1, 'name': 'Test room', 'countryName': 'Russia', 'status': False}}, {{'id': 2, 'name': 'Test room bel', 'countryName': 'Belarus', 'status': False}}]"
    print(f'\nExpected value: {expected_value}')
    response_code = requests.get(host_url+"rooms/")
    print('\nThe response for this GET request is')
    print(response_code)
    response_result = (json.dumps(response_code.json()))
    response_value = str(json.loads(response_result))
    print(f'\nResponse value: {response_value}')

    print(f'\nValidation result: {expected_value == response_value}')

#GET /api/rooms/id
def getValidRoomTest(room_id):
    print('\n*************')
    print('\nTest get room with valid id')
    expected_value = f"{{'id': {room_id}, 'name': 'PyRoom', 'countryName': 'Belarus', 'status': False}}"
    print(f'\nExpected value: {expected_value}')

    response_code = requests.get(host_url+f"rooms/{room_id}")
    print('\nThe response for this GET request is')
    print(response_code)
    response_result = (json.dumps(response_code.json()))
    response_value = str(json.loads(response_result))
    print(f'\nResponse value: {response_value}')

    print(f'\nTest validation result: {expected_value == response_value}')

#GET /api/rooms/id
def getInvalidRoomTest():
    print('\n*************')
    print('\nTest get room with invalid id')
    expected_value = requests.codes.not_found
    print(f'\nExpected value: {expected_value}')
    
    response_code = requests.get(host_url+"rooms/-1")
    print('\nThe response for this GET request is')
    print(response_code)
    print(f'\nTest validation result: {expected_value == response_code.status_code}')

#GET /api/rooms/id
def getRoomWithDifferentIpTest():
    print('\n*************')
    print('\nTest get room with ip other than user')
    expected_value = requests.codes.forbidden
    print(f'\nExpected value: {expected_value}')

    response_code = requests.get(host_url+'rooms/1')
    print('\nThe response for this GET request is')
    print(response_code)
    print(f'\nTest validation result: {expected_value == response_code.status_code}')

#POST /api/rooms
def createRoomTest():
    print('\n*************')
    print('\nTest create room')
    expected_value = requests.codes.ok
    print(f'\nExpected value: {expected_value}')
    data = {
        "name": "PyRoom",
        "countryName": "Belarus",
        "status": False
    }
    body = json.dumps(data)
    header = {'Content-Type': 'application/json'}
    response_code = requests.post(host_url+"rooms", headers=header, data=body)
    print('\nThe response for this POST request is')
    print(response_code)
    response_result = (json.dumps(response_code.json()))
    print(response_result)
    print(f'\nTest validation result: {expected_value == response_code.status_code}')

#PUT /api/rooms/id/activate
def updateRoomTest(room_id):
    print('\n*************')
    print('\nTest update room')
    expected_value = requests.codes.ok
    print(f'\nExpected value: {expected_value}')
    response_code = requests.put(host_url+f"rooms/{room_id}/activate")
    print('\nThe response for this PUT request is')
    print(response_code)
    print(f'\nTest validation result: {expected_value == response_code.status_code}')

#PUT /api/rooms/id/activate
def updateRoomWithInvalidIdTest():
    print('\n*************')
    print('\nTest update room with invalid id')
    expected_value = requests.codes.not_found
    print(f'\nExpected value: {expected_value}')
    response_code = requests.put(host_url+f"rooms/-1/activate")
    print('\nThe response for this PUT request is')
    print(response_code)
    print(f'\nTest validation result: {expected_value == response_code.status_code}')

if __name__ == '__main__':
    #Connect to db
    connection = connect()
    getAllRoomsFromDb(connection)
    #Tests
    getRoomsTest()
    addRoomToDb(connection)
    room_id = getAddedRoomId(connection)
    getValidRoomTest(room_id[0])
    getInvalidRoomTest()
    getRoomWithDifferentIpTest()
    createRoomTest()
    updateRoomTest(room_id[0])
    updateRoomWithInvalidIdTest()
    #Clear DB
    deleteNewRoom(connection)
    #Close connection
    closeConnection(connection)