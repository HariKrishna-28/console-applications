
from ast import Try
import os
from random import choice


def clear_screen():
    if os.name == 'posix':
        os.system('clear')
    else:
        os.system('cls')


def get_names(type, valid_domains=["@gmail.com", "@yahoo.com"]):
    print("{} Login".format(type.capitalize()))
    while True:
        name = input("Enter your Email Address : ")
        password = input("Enter password : ")
        try:
            if name[name.index("@"):] in valid_domains:
                return [name, password]
        except ValueError:
            clear_screen()
            print(
                "Invalid email. Make sure your email address ends with @gmail.com/@yahoo.com")


def authenticate_user(name, password, database):
    for keys, values in database.items():
        if name == keys and password == values:
            return True
    return False


def get_price():
    while True:
        try:
            print("Wallet Recharge")
            n = int(input("Enter the price : "))
            return n
        except ValueError:
            clear_screen()
            print("Invalid input")
            continue


def create_contact_group(name, database):
    database[name] = [name]
    return database


def modify_contacts(name, database, user_details, add=True):
    updated_database = database
    while True:
        try:
            r = int(input("Enter the number of users : "))
            for i in range(r):
                print("Enter exit if you want to exit this window")
                user_name = input("Enter the id of the user : ")
                if user_name.lower() == "exit":
                    return updated_database
                else:
                    if user_name not in user_details:
                        clear_screen()
                        print("No such user")
                    else:
                        data = updated_database[name]
                        if add:
                            data.append(user_name)
                        if not add:
                            try:
                                data.remove(user_name)
                            except ValueError:
                                clear_screen()
                                print("No such user")
            return updated_database
        except ValueError:
            clear_screen()
            print("Invalid input")
            continue


user_credentials = {"hari@gmail.com": "123",
                    "krishna@gmail.com": "321", "kowsik@gmail.com": "1212", "dina@gmail.com": "1232", "hk@gmail.com": "123"}

wallet_amount = {"hari@gmail.com": 35000, "krishna@gmail.com": 20000,
                 "kowsik@gmail.com": 0, "dina@gmail.com": 500000, "hk@gmail.com": 5000}

contact_groups = {
    "hk@gmail.com": ["hk@gmail.com", "hari@gmail.com", "kowsik@gmail.com"], }

# due_amounts = {}

# Driver code
clear_screen()
while True:
    while True:
        try:
            print("Welcome to splitwise application")
            choice = int(input("1. Create Account\n2. Login\n3. Exit\n"))
            if choice > 3:
                clear_screen()
                print("Invalid choice")
            else:
                break
        except ValueError:
            clear_screen()
            print("Invalid input")
            continue

    if choice == 1:
        clear_screen()
        while True:
            name, password = get_names(type="new user")
            if name not in user_credentials:
                user_credentials[name] = password
                wallet_amount[name] = 0
                contact_groups = create_contact_group(name, contact_groups)
                clear_screen()
                print("Successfully created an account. Login to continue")
                break
            else:
                clear_screen()
                print("Email already exists. Please pick a different one")

    if choice == 2:
        clear_screen()
        name, password = get_names(type="user")
        print(name, password)
        if authenticate_user(name, password, user_credentials):
            clear_screen()
            print("Welcome user {}".format(
                name[:name.index("@")]).capitalize())
            while True:
                while True:
                    try:
                        user_choice = int(input(
                            "1. Add Expense\n2. Update Wallet\n3. Add Contacts\n4. Remove Friends\n5. View Dues\n6. View Transaction History\n7. Logout\n"))
                        if user_choice > 7:
                            clear_screen()
                            print("invalid choice")
                        else:
                            break
                    except ValueError:
                        clear_screen()
                        print("Invalid entry")
                        continue

                if user_choice == 2:
                    clear_screen()
                    price = get_price()
                    print(wallet_amount)
                    wallet_amount[name] = wallet_amount[name]+price
                    print(wallet_amount)

                if user_choice == 7:
                    clear_screen()
                    break

                if user_choice == 3:
                    clear_screen()
                    prev_length = len(contact_groups[name])
                    contact_groups = modify_contacts(
                        name, contact_groups, user_credentials, add=True)
                    print("Added {} contacts".format(
                        prev_length-len(contact_groups[name])))

                if user_choice == 4:
                    clear_screen()
                    print(contact_groups[name])
                    if len(contact_groups[name]) == 1:
                        clear_screen()
                        print("Empty contacts")
                        continue
                    else:
                        clear_screen()
                        prev_length = len(contact_groups[name])
                        contact_groups = modify_contacts(
                            name, contact_groups, user_credentials, add=False)
                        print("Removed {} contacts".format(
                            prev_length-len(contact_groups[name])))

        else:
            print("Invalid credentials")

    if choice == 3:
        clear_screen()
        break
