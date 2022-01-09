# Console shopping application that has functionalities similar to amazon
# All the user/admin credentials and available datas are static and resets
# to default once the application has stopped runninng.

import os


def clear_screen():
    if os.name == 'posix':
        os.system('clear')
    else:
        os.system('cls')


def get_names(type):
    clear_screen()
    print("{} Login".format(type.capitalize()))
    while True:
        name = input("Enter your name : ")
        password = input("Enter password : ")
        if name.isalpha() and password.isdigit():
            return [name, int(password)]
        else:
            clear_screen()
            print("Name should be a string and password should be an Integer")


def authenticate_user(name, password, database):
    for keys, values in database.items():
        if name == keys and password == values:
            return True
    return False


def approve_data(database, merchant_database, type, new_database={}, updated_database={}):
    clear_screen()
    for keys, items in database.items():
        if type.lower() == "merchant":
            choice = input(
                "Do you want to approve {} as an authorised Merchant (Y/N)? : ".format(keys))
        if type.lower() == "product":
            p = {}
            p[keys] = items
            print_products(p)
            # print("Name : {}\nQuantity : {}\nPrice : {}".format(
            #     keys, items[0], items[-1]))
            choice = input(
                "Do you want to approve {} as a product (Y/N)? : ".format(keys))
        if choice.lower() == "y":
            new_database[keys] = database[keys]
        if choice.lower() == "n":
            updated_database[keys] = items

    return updated_database, merchant_database | new_database


def print_products(products):
    clear_screen()
    print("{:<4} {:<18} {:<10} {:<10}".format(
        "SNo", "Name", "Quantity", "Price"))
    for index, (keys, values) in enumerate(products.items()):
        print("{:<4} {:<18} {:<10} {:<10}".format(
            index+1, keys, values[0], values[-1]))


# Data
admin_credentials = {"Hario": 12345}
merchant_credentials = {"hariMerchant": 54321,
                        "merchantHari": 1221, "merch": 1111}
user_credentials = {"Har": 123, "krishna": 321}
# productName = [quantity, price]
products = {"Washing Machine": [500, 5000],
            "Fridge": [300, 35000], "Phone": [1000, 7000], }
merchant_approval_queue = {"Merchant1": 1000, "Merchant2": 9999}
product_approval_queue = {"Speaker": [8000, 350], "Computer": [123, 10000]}


# driver code
clear_screen()

while True:
    print("Online Shopping Application")
    while True:
        try:
            choice = int(
                input(("1. Admin Login\n2. Merchant Login\n3. User Login\n4. Exit\n")))
            if choice > 4:
                clear_screen()
                print("Enter a valid option")
            else:
                break

        except ValueError:
            clear_screen()
            print("Invalid Entry")

    if choice == 1:
        clear_screen()
        name, password = get_names(type="admin")
        if authenticate_user(name, password, admin_credentials):
            clear_screen()
            print("Welcome Admin {}".format(name.capitalize()))
            while True:
                while True:
                    try:
                        admin_choice = int(input(
                            "1. Approve New Merchants\n2. View Products\n3. Approve Product\n4. Exit \n"))
                        if (admin_choice > 5):
                            clear_screen()
                            print("Enter a valid option")
                        else:
                            break
                    except ValueError:
                        clear_screen()
                        print("Invalid Entry")

                if admin_choice == 4:
                    break

                if admin_choice == 1:
                    if not merchant_approval_queue:
                        clear_screen()
                        print("Empty queue")
                    else:
                        clear_screen()
                        previous_count = len(merchant_credentials)
                        merchant_approval_queue, merchant_credentials = approve_data(
                            merchant_approval_queue, merchant_credentials, type="merchant")
                        print("Added {} new users".format(
                            len(merchant_credentials)-previous_count))

                if admin_choice == 2:
                    print_products(products)

                if admin_choice == 3:
                    if not product_approval_queue:
                        clear_screen()
                        print("Empty queue")
                    clear_screen()
                    previous_count = len(products)
                    product_approval_queue, products = approve_data(
                        product_approval_queue, products, type="product")
                    print("Added {} new products".format(
                        len(products)-previous_count))
        else:
            print("Invalid Username or Password")
