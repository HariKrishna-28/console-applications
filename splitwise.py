# Splitwise application. Head to line 150 for static data and 167 for the driver code

import os
import math


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


def print_expenses(database):
    print("{:<4} {:<18} {:<18}".format("ID", "Expense", "Cost\n"))
    for index, (keys, values) in enumerate(database.items()):
        print("{:<4} {:<18} {:<18}".format(index+1, keys, values))


def validate_payment(database, due, total, sum=0):
    for balance in database.values():
        sum += balance
        if balance < due:
            return False
    return True if sum > total else False


def initiate_transfer(wallet_amount, transaction_history, share_price, contact_data, current_expense, shared):
    for name in contact_data.keys():
        if shared:
            wallet_amount[name] -= share_price
        else:
            wallet_amount[name] -= contact_data[name]
        transaction_data = transaction_history[name]
        transaction_data[current_expense] = share_price
    return [wallet_amount, transaction_history]


def get_payment_details(contact_data, wallet_amount, payment_due, sum=0, expense_details={}):
    while True:
        for names in contact_data:
            print("Name: {} Remaining Amount : {}".format(names, payment_due-sum))
            while True:
                try:
                    user_amount = int(
                        input("Enter the share of {} : ".format(names)))
                    if wallet_amount[names] < user_amount:
                        print("Not enough funds in {}'s account".format(name))
                    else:
                        sum += user_amount
                        expense_details[names] = user_amount
                        if sum == payment_due:
                            return expense_details
                        else:
                            break
                except ValueError:
                    clear_screen()
                    print("Invalid Entry")

        return expense_details


def validate_shares(expense_details, payment_due, sum=0):
    for wallet_balance in expense_details.values():
        sum += wallet_balance
        print(sum, wallet_amount, payment_due)
    return sum > payment_due


# def update_transactions(expense_details, transaction_history, expense_name):
#     for name, amount in expense_details.items():
#         print(amount)
#         transaction_data = transaction_history[name]
#         if transaction_history[name]:
#             transaction_data[expense_name] += amount
#         else:
#             transaction_data[expense_name] = amount
#     return transaction_history


user_credentials = {"hari@gmail.com": "123",
                    "krishna@gmail.com": "321", "kowsik@gmail.com": "1212", "dina@gmail.com": "1232", "hk@gmail.com": "123"}

wallet_amount = {"hari@gmail.com": 35000, "krishna@gmail.com": 20000,
                 "kowsik@gmail.com": 10000, "dina@gmail.com": 500000, "hk@gmail.com": 5000}

contact_groups = {
    "hk@gmail.com": ["hk@gmail.com", "hari@gmail.com", "kowsik@gmail.com"], }

expenses = {"hari@gmail.com": {"shopping": 2000, "transport": 25000}, "krishna@gmail.com": {}, "kowsik@gmail.com": {
    "food": 5000}, "dina@gmail.com": {}, "hk@gmail.com": {"shopping": 2000, "transport": 25000, "electricity": 50000}}

transaction_history = {"hari@gmail.com": {"shopping": 2000, "transport": 5000}, "krishna@gmail.com": {}, "kowsik@gmail.com": {
    "food": 5000}, "dina@gmail.com": {}, "hk@gmail.com": {"shopping": 200, "transport": 500, "electricity": 500}}

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
                print(expenses)
                expenses[name] = {}
                transaction_history[name] = {}
                print(expenses)
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

                if user_choice == 1:
                    clear_screen()
                    while True:
                        try:
                            expense_name = input(
                                "Enter the name of the expense : ")
                            amount = int(input("Enter the price : "))
                            data = expenses[name]
                            data[expense_name] = amount
                        except ValueError:
                            clear_screen()
                            print("Invalid entry")
                            continue
                        print("Successfully added an expense")
                        break

                if user_choice == 2:
                    clear_screen()
                    price = get_price()
                    wallet_amount[name] = wallet_amount[name]+price

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

                if user_choice == 5:
                    clear_screen()
                    if not expenses[name]:
                        print("No expenses")
                        continue
                    else:
                        clear_screen()
                        print("Contact Groups")
                        print(*contact_groups[name])
                        print("Expenses for {}".format(name))
                        payment_completed_flag = False
                        shared_payment_completed_flag = False
                        if payment_completed_flag or shared_payment_completed_flag:
                            break
                        else:
                            print_expenses(expenses[name])
                            while True:
                                try:
                                    expense_choice = int(
                                        input("Enter the ID of the expense you want to pay : "))
                                    if expense_choice > len(expenses[name]):
                                        clear_screen()
                                        print("No such expense")
                                        continue
                                    else:
                                        expense_data = list(expenses[name])
                                        current_expense = expense_data[expense_choice-1]
                                        payment_due = expenses[name][current_expense]
                                        break
                                except ValueError:
                                    clear_screen()
                                    print("No such value")
                                    continue

                            clear_screen()
                            print("Choose a method to pay your {} bill of Rs.{}".format(
                                current_expense, payment_due))
                            while True:
                                try:
                                    payment_choice = int(
                                        input("1. Split equally among friends\n2. Split Manually\n3. Exit\n"))
                                    if payment_choice > 3:
                                        clear_screen()
                                        print("Invalid range")
                                        continue
                                    break
                                except ValueError:
                                    clear_screen()
                                    print("Invalid entry")
                                    continue

                            if payment_choice == 3:
                                clear_screen()
                                break

                            if payment_choice == 1:
                                clear_screen()
                                contact_data = contact_groups[name]
                                expense_details = {}
                                expense_details[name] = wallet_amount[name]
                                for names in contact_data:
                                    expense_details[names] = wallet_amount[names]
                                print(expense_details)
                                share_price = math.ceil(
                                    payment_due/len(contact_data))
                                if validate_payment(expense_details, payment_due, share_price):
                                    print("Everyone should pay Rs.{} each".format(
                                        payment_due))
                                    while True:
                                        try:
                                            final_payment_choice = input(
                                                "Do you want to pay(y/n)")
                                            if final_payment_choice.lower() == 'n':
                                                clear_screen()
                                                print("Payment cancelled")
                                                payment_completed_flag = True
                                                break
                                            if final_payment_choice.lower() == 'y':
                                                clear_screen()
                                                wallet_amount, transaction_history = initiate_transfer(
                                                    wallet_amount, transaction_history, share_price, expense_details, current_expense, shared=True)
                                                print("Payment success")
                                                updated_payment = expenses[name]
                                                del updated_payment[current_expense]
                                                expenses[name] = updated_payment
                                                payment_completed_flag = True
                                                break
                                        except ValueError:
                                            clear_screen()
                                            print("Invalid choice")
                                            continue
                                else:
                                    clear_screen()
                                    print("Insufficient funds")
                                    break

                            if payment_choice == 2:
                                clear_screen()
                                contact_data = contact_groups[name]
                                expense_details = get_payment_details(
                                    contact_data, wallet_amount, payment_due)
                                if validate_shares(expense_details, payment_due):
                                    print(
                                        "Not enough funds. Update wallet or add new members")
                                    break
                                else:
                                    while True:
                                        try:
                                            final_payment_choice = input(
                                                "Do you want to pay(y/n)")
                                            if final_payment_choice.lower() == 'n':
                                                clear_screen()
                                                print("Payment cancelled")
                                                shared_payment_completed_flag = False
                                                break
                                            if final_payment_choice.lower() == 'y':
                                                clear_screen()
                                                wallet_amount, transaction_history = initiate_transfer(
                                                    wallet_amount, transaction_history, 50, expense_details, current_expense, shared=False)
                                                print("Payment success")
                                                shared_payment_completed_flag = False
                                                updated_payment = expenses[name]
                                                del updated_payment[current_expense]
                                                expenses[name] = updated_payment
                                                break
                                        except ValueError:
                                            clear_screen()
                                            print("Invalid choice")
                                            continue

                if user_choice == 6:
                    clear_screen()
                    print_expenses(transaction_history[name])

        else:
            print("Invalid credentials")

    if choice == 3:
        clear_screen()
        break
