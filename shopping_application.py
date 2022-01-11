# Console shopping application that has functionalities similar to amazon
# All the user/admin credentials and available datas are static and resets
# to default once the application has stopped runninng.
# Head to line 153 for data and 172 for the driver code

import os
from random import randint


def clear_screen():
    if os.name == 'posix':
        os.system('clear')
    else:
        os.system('cls')


def get_names(type):
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


def remove_merchants(database, new_database={}):
    for keys, items in database.items():
        while True:
            choice = input(
                "Do you want to remove {} as an authorised merchant (Y/N) : ".format(keys.capitalize()))
            if choice.lower() not in ["y", "n"]:
                clear_screen()
                print("Invalid input")
            else:
                if choice.lower() == "n":
                    new_database[keys] = items
                break
    return new_database


def print_products(products, seller="", name=""):
    print("{:<4} {:<18} {:<18} {:<10} {:<10}".format(
        "Id", "Name", "Seller", "Quantity", "Price"))
    for index, (keys, values) in enumerate(products.items()):
        if not seller and not name:
            print("{:<4} {:<18} {:<18} {:<10} {:<10}".format(
                index+1, keys, values[-1], values[0], values[1]))

        else:
            if seller:
                if seller.lower() == values[-1].lower():
                    print("{:<4} {:<18} {:<18} {:<10} {:<10}".format(
                        index+1, keys, values[-1], values[0], values[1]))
            if name:
                if name.lower() == keys.lower():
                    print("{:<4} {:<18} {:<18} {:<10} {:<10}".format(
                        index+1, keys, values[-1], values[0], values[1]))


def print_merchants(merchants):
    print("{:<4} {:<18} ".format("Id", "Name"))
    for index, (keys) in enumerate(merchants.keys()):
        print("{:<4} {:<18} ".format(index, keys))


def check_data(name, database):
    return name in database


def receive_products(new_product=False):
    while True:
        try:
            name = input("Enter the name of the product : ")
            quantity = int(input("Enter the quantity : "))
            if not new_product:
                return [name, quantity]
            else:
                price = int(input("Enter the price : "))
                return[name, quantity, price]
        except ValueError:
            clear_screen()
            print("Invalid Entry")


def validate_product_entry(db, product, user_name):
    for key, vals in db.items():
        if key.lower() == product.lower() and vals[-1].lower() == user_name.lower():
            return True
    return False


def restock_shelf(db, p_name, quantity):
    old_quantity = db[p_name]
    old_quantity[0] += quantity
    return db


def update_items(db, p_name, product, quantity):
    if p_name in db:
        db[p_name].append([product, quantity])
    else:
        db[name] = [[product, quantity]]
    return db


def show_items(db, name):
    if name not in db:
        clear_screen()
        print("Empty")
    else:
        clear_screen()
        print("{:<4} {:<18} {:<25}".format(
            "SNo", "Name", "Quantity"))
        for keys, items in db.items():
            for i in items:
                if keys.lower() == name.lower():
                    print("{:<4} {:<18} {:<25}".format(
                        items.index(i)+1, i[0], i[-1]))

    # Data
admin_credentials = {"Hario": 12345}
merchant_credentials = {"hariMerchant": 54321,
                        "merchantHari": 1221, "merch": 1111}
user_credentials = {"Har": 123, "krishna": 321, "kowsik": 1212, "dina": 1232}
# productName = [quantity, price, seller]
products = {"Washing machine": [500, 5000, "hariMerchant"],
            "Fridge": [300, 35000, "merchantHari"], "Phone": [1000, 7000, "merch"],
            "Washing Machine": [50, 7000, "merchantHari"]}
merchant_approval_queue = {"MerchA": 1000, "MerchB": 9999}
product_approval_queue = {"Speaker": [
    8000, 350, "merch"], "Computer": [123, 10000, "hariMerchant"]}
# userName = [name of the product, quantity]
cart = {"Har": [["Fridge", 2]], "dina": [
    ["Phone", 1]], "kowsik": [["Washing Machine", 1]]}

shopping_history = {"kowsik": [["Washing Machine", 2]]}


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
        name, password = get_names(type="admin")
        if authenticate_user(name, password, admin_credentials):
            clear_screen()
            while True:
                while True:
                    try:
                        # clear_screen()
                        print("Welcome Admin {}".format(name.capitalize()))
                        admin_choice = int(input(
                            "1. Approve New Merchants\n2. View Products\n3. Remove Existing Merchants\n4. Approve Product\n5. Available Merchants\n6. Available Products\n7. Exit \n"))
                        if (admin_choice > 7):
                            clear_screen()
                            print("Enter a valid option")
                        else:
                            clear_screen()
                            break
                    except ValueError:
                        clear_screen()
                        print("Invalid Entry")

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

                if admin_choice == 4:
                    if not product_approval_queue:
                        clear_screen()
                        print("Empty queue")
                    clear_screen()
                    previous_count = len(products)
                    product_approval_queue, products = approve_data(
                        product_approval_queue, products, type="product")
                    print("Added {} new products".format(
                        len(products)-previous_count))

                if admin_choice == 3:
                    clear_screen()
                    previous_count = len(merchant_credentials)
                    merchant_credentials = remove_merchants(
                        merchant_credentials)
                    print("Removed {} merchants".format(
                        previous_count-len(merchant_credentials)))

                if admin_choice == 5:
                    clear_screen()
                    print("\nAvailable merchants:")
                    print_merchants(merchant_credentials)
                    print("\nWaiting for approval")
                    print_merchants(merchant_approval_queue)

                if admin_choice == 6:
                    clear_screen()
                    print("\nAvailable Products")
                    print_products(products)
                    print("\nWaiting Products")
                    print_products(product_approval_queue)

                if admin_choice == 7:
                    break

        else:
            clear_screen()
            print("Invalid Username or Password")

    if choice == 2:
        clear_screen()
        while True:
            while True:
                try:
                    # clear_screen()
                    print("Merchant Portal")
                    merchant_choice = int(input(
                        "1. Become a Merchant\n2. Merchant Login\n3. Check Approval Status\n4. Exit \n"))
                    if (merchant_choice > 4):
                        clear_screen()
                        print("Enter a valid option")
                    else:
                        break
                except ValueError:
                    clear_screen()
                    print("Invalid Entry")

            if merchant_choice == 1:
                while True:
                    # clear_screen()
                    name, password = get_names(type="new merchant")
                    if name not in merchant_approval_queue and name not in merchant_credentials:
                        merchant_approval_queue[name] = password
                        clear_screen()
                        print("Your request is waiting for approval")
                        break
                    else:
                        clear_screen()
                        print("Theres already a merchant named {}. Try a different name".format(
                            name))

            if merchant_choice == 2:
                clear_screen()
                name, password = get_names(type="merchant")

                if authenticate_user(name, password, merchant_credentials):
                    clear_screen()
                    while True:
                        while True:
                            try:
                                print("Welcome merchant {}!".format(
                                    name.capitalize()))
                                verified_merchant_choice = int(input(
                                    "1. Restock Products\n2. View Products\n3. Request for a new product\n4. Exit \n"))
                                if (merchant_choice > 4):
                                    clear_screen()
                                    print("Enter a valid option")
                                else:
                                    break
                            except ValueError:
                                clear_screen()
                                print("Invalid Entry")

                        if verified_merchant_choice == 1:
                            product_name, quantity = receive_products()
                            if validate_product_entry(products, product_name, name):
                                products = restock_shelf(
                                    products, product_name, quantity)
                                clear_screen()
                                print("Updated the quantity of the products")
                                print("New Quantity : {} units".format(
                                    products[product_name][0]))
                            else:
                                clear_screen()
                                print("You dont own that product!")

                        if verified_merchant_choice == 2:
                            clear_screen()
                            print_products(products, seller=name)

                        if verified_merchant_choice == 3:
                            new_product_name, quantity, price = receive_products(
                                new_product=True)
                            if validate_product_entry(products, new_product_name, name):
                                clear_screen()
                                print(
                                    "Product already exists. Restock them instead of adding a new one")
                            else:
                                product_approval_queue[new_product_name] = [
                                    quantity, price, name]
                                clear_screen()
                                print("Waiting for approval")

                        if verified_merchant_choice == 4:
                            clear_screen()
                            break

                else:
                    clear_screen()
                    print("Invalid username or password")

            if merchant_choice == 3:
                clear_screen()
                name, password = get_names(type="merchant approval")
                if name in merchant_approval_queue:
                    clear_screen()
                    print(
                        "Your profile is waiting for approval by the admin. Please login and check later")
                if name in merchant_credentials:
                    clear_screen()
                    print("Merchant request approved")
                if name not in merchant_approval_queue and name not in merchant_credentials:
                    clear_screen()
                    print(
                        "Were sorry to tell you that your approval has been rejected.")

            if merchant_choice == 4:
                clear_screen()
                break

    if choice == 3:
        clear_screen()
        while True:
            while True:
                try:
                    print("User Login")
                    user_choice = int(input("1. Login\n2. Signup\n3. Exit\n"))
                    if user_choice > 3:
                        clear_screen()
                        print("Invalid Choice")
                    else:
                        break
                except ValueError:
                    clear_screen()
                    print("Invalid Entry")

            if user_choice == 2:
                name, password = get_names(type="New User")
                if name not in user_credentials:
                    user_credentials[name] = password
                    clear_screen()
                    print("Added your credentials. Login to continue")
                    continue
                else:
                    clear_screen()
                    print("User already exists. Try a different user name")

            if user_choice == 3:
                clear_screen()
                break

            if user_choice == 1:
                name, password = get_names(type="User")
                if authenticate_user(name, password, user_credentials):
                    clear_screen()
                    while True:
                        print("Welcome user {}". format(name.capitalize()))
                        while True:
                            try:
                                verified_user_choice = int(
                                    input("1. Available Products\n2. View Cart\n3. History\n4. Exit\n"))
                                if verified_user_choice > 4:
                                    clear_screen()
                                    print("Invalid Choice")
                                else:
                                    break
                            except ValueError:
                                clear_screen()
                                print("Invalid Entry")

                        if verified_user_choice == 4:
                            clear_screen()
                            break

                        if verified_user_choice == 1:
                            clear_screen()
                            print_products(products)
                            while True:
                                while True:
                                    try:
                                        product_choice = input(
                                            "Wnat would you like to buy : ")
                                        product_quantity = int(
                                            input("Enter quantity : "))
                                        break

                                    except ValueError:
                                        clear_screen()
                                        print("Invalid Entry")

                                if product_choice.lower().capitalize() in products:
                                    while True:
                                        try:
                                            buyer_choice = int(
                                                input("1. Buy Now\n2. Add to Cart\n3. Exit\n"))
                                            if buyer_choice > 3:
                                                clear_screen()
                                                print("Invalid Entry")
                                            else:
                                                break
                                        except ValueError:
                                            clear_screen()
                                            print("Invalid Entry")
                                    if buyer_choice == 1:
                                        clear_screen()
                                        print("Available Offers")
                                        print_products(
                                            products, name=product_choice)
                                        seller_name = input(
                                            "Choose a seller : ")
                                        clear_screen()
                                        try:
                                            val = products[product_choice.lower(
                                            ).capitalize()]
                                            if val[0] < product_quantity:
                                                clear_screen()
                                                print(
                                                    "Out of stock. Try a different buyer")
                                            else:
                                                val[0] -= product_quantity
                                                shopping_history = update_items(
                                                    cart, name, product_choice, product_quantity)
                                                address = input(
                                                    "Enter your address : ")
                                                print("{} will be delivered to {} in {} business days". format(
                                                    product_choice, address, randint(1, 8)))
                                                break
                                        except KeyError:
                                            clear_screen()
                                            print("Seller not available")

                                    if buyer_choice == 2:
                                        cart = update_items(
                                            cart, name, product_choice, product_quantity)
                                        clear_screen()
                                        print("Added {} to your cart. Your cart now has {} items".format(
                                            product_choice, len(cart[name])))
                                        break

                                else:
                                    clear_screen()
                                    print("No Products Found")

                        if verified_user_choice == 2:
                            show_items(cart, name)
                            continue

                        if verified_user_choice == 3:
                            show_items(shopping_history, name)
                            continue

                else:
                    print("Invalid name")
