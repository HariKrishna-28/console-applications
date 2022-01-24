
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


user_credentials = {"hari@gmail.com": "123",
                    "krishna@gmail.com": "321", "kowsik@gmail.com": "1212", "dina@gmail.com": "1232"}

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
                            "1. Add Expense\n2. Update Wallet\n3. Create Contact Group\n4. Add Contacts\n5. Remove Friends\n6. View Dues\n7. View Transaction History\n8. Logout\n"))
                        if user_choice > 8:
                            clear_screen()
                            print("invalid choice")
                        else:
                            break
                    except ValueError:
                        clear_screen()
                        print("Invalid entry")
                        continue

                if user_choice == 8:
                    clear_screen()
                    break

        else:
            print("Invalid credentials")

    if choice == 3:
        clear_screen()
        break
