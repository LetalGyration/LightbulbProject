from os import error
import psycopg2
from requests import exceptions
from config import config
import requests
import json

def connect():
    """ Connect to the PostgreSQL database server """
    conn = None
    pg_query = None
    host_url = "http://localhost:8080/api/"
    response_code = None
    response_result = None
    response_value = None
    expected_value = None
    body = None
    try:
        #read parameters of connection
        params = config()

        #connection to server
        print('Connecting to the PostgreSQL database')
        conn = psycopg2.connect(**params)
        
        curs = conn.cursor()

        print('Room list in database:')
        pg_query = """ SELECT * FROM room """
        curs.execute(pg_query)
        db_list = curs.fetchall()
        print(db_list)

        expected_value = f"[{{'id': 1, 'name': 'Test room', 'countryName': 'Russia', 'status': False}}, {{'id': 2, 'name': 'Test room bel', 'countryName': 'Belarus', 'status': False}}]"
        print(f'\nExpected value: {expected_value}')
        #GET /api/rooms
        response_code = requests.get(host_url+"rooms/")
        print('\nThe response for this GET request is')
        print(response_code)
        if(response_code.status_code == 403):
            raise requests.RequestException
        response_result = (json.dumps(response_code.json()))
        response_value = str(json.loads(response_result))
        print(f'\nResponse value: {response_value}')

        print(f'\nValidation result: {expected_value == response_value}')

        print('\nAdding new room in table')
        pg_query = """ INSERT INTO room (room_name, country_name, status) VALUES (%s, %s, %s) """
        record = ('PyRoom', 'Belarus', False)
        curs.execute(pg_query, record)
        conn.commit()
        
        print('\nUpdated room list:')
        pg_query = """ SELECT * FROM room """
        curs.execute(pg_query)
        db_list = curs.fetchall()
        print(db_list)
        
        #get room_id
        pg_query = """SELECT room_id FROM room WHERE room_name = %s """
        record = ('PyRoom',)
        curs.execute(pg_query, record)
        room_id = curs.fetchone()
        expected_value = f"{{'id': {room_id[0]}, 'name': 'PyRoom', 'countryName': 'Belarus', 'status': False}}"
        print(f'\nExpected value: {expected_value}')

        #GET /api/rooms/id
        response_code = requests.get(host_url+f"rooms/{room_id[0]}")
        print('\nThe response for this GET request is')
        print(response_code)
        if(response_code.status_code == 403):
            raise requests.RequestException
        response_result = (json.dumps(response_code.json()))
        response_value = str(json.loads(response_result))
        print(f'\nResponse value: {response_value}')

        print(f'\nValidation result: {expected_value == response_value}')

        print('\nUpdated list:')
        pg_query = """ SELECT * FROM room """
        curs.execute(pg_query)
        db_list = curs.fetchall()
        print(db_list)

        print('\nClear table of new rooms')
        pg_query = """ DELETE FROM room WHERE room_name = %s """
        record = ('PyRoom',)
        curs.execute(pg_query, record)
        conn.commit()

        #POST /api/rooms
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

        #get room_id
        pg_query = """SELECT room_id FROM room WHERE room_name = %s """
        record = ('PyRoom',)
        curs.execute(pg_query, record)
        room_id = curs.fetchone()

        print('\nUpdated list:')
        pg_query = """ SELECT * FROM room """
        curs.execute(pg_query)
        db_list = curs.fetchall()
        print(db_list)

        #PUT /api/rooms/id/activate
        response_code = requests.put(host_url+f"rooms/{room_id[0]}/activate")
        print('\nThe response for this PUT request is')
        print(response_code)

        #get room
        pg_query = f"""SELECT * FROM room WHERE room_id = {room_id[0]} """
        curs.execute(pg_query)
        expected_value = curs.fetchone()
        print(expected_value)

        print('\nDeleting new rooms from table')
        pg_query = """ DELETE FROM room WHERE room_name = %s """
        record = ('PyRoom',)
        curs.execute(pg_query, record)
        conn.commit()
        
        conn.close()

    except (Exception, psycopg2.DatabaseError) as error:
        print(error)
    finally:
        if conn is not None:
            conn.close()
            print('\nDatabase connection closed')

if __name__ == '__main__':
    connect()