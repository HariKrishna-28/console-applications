import os


def clear_screen():
    if os.name == 'posix':
        os.system('clear')
    else:
        os.system('cls')


def get_names(admin_login=False):
    clear_screen()
    print("Admin Login" if admin_login else "User Login")
    while True:
        try:
            name = input("Enter your name : ")
            password = int(input("Enter your password : "))
            clear_screen()
            break
        except ValueError:
            clear_screen()
            print(
                "Invalid Input\n Name Should be a string and password should be an integer")
    return [name, password]


def check_values(user, password, database):
    for keys, values in database.items():
        if user == keys and password == values:
            return True
    return False


def authenticate_user(database, name, password, admin=False):
    # stored_admin_data = {"Hario": 12345}
    # stored_user_data = {"kisna": 12345,
    #                     "kaggleDhina": 123456, "dhina": 1234567, "har": 321}

    if admin:
        return True if check_values(name, password, database) else False
    if not admin:
        return True if check_values(name, password, database) else False


def find_balance(denominations, bal=0):
    for keys, vals in denominations.items():
        bal += keys*vals
    return bal


def return_bal(amt, bal=0, vals=[2000, 1000, 500, 200, 100]):
    for i in range(len(amt)):
        bal += vals[i]*amt[i]
    return bal


def validate_denominations(denominations, available_denominations):
    max_threshold = {2000: 50, 1000: 50, 500: 50, 200: 50, 100: 50}
    for index, (val) in enumerate(max_threshold):
        if denominations[index] + available_denominations[val] > max_threshold[val]:
            return False
    return True


def update_money(old, new, updated={}):
    for index, (key, value) in enumerate(old.items()):
        updated[key] = value + new[index]
    return updated


def change_pin(database, name, new_pin):
    database[name] = new_pin
    return database


def get_money():
    clear_screen()
    print("Enter the denominations below")
    print("2000 1000 500 200 100\n")
    demonimations = list(map(int, input().split()))
    return demonimations


def generate_denominations(amt, denominations=[], vals=[2000, 1000, 500, 200, 100]):
    for i in vals:
        denominations.append(amt//i)
        amt = amt % i
    return denominations


def validate_transfer(name, amount, balance_database, threshold):
    # return amount > balance_database[name]
    return True if amount < balance_database[name] and threshold[name]+amount < 200000 else False


def transfer_funds(from_name, to_name, amount, balance_database):
    balance_database[from_name] = balance_database[from_name] - amount
    balance_database[to_name] = balance_database[to_name] + amount
    return balance_database


def validate_atm_funds(amount, available_denominations):
    return amount < find_balance(available_denominations)


def get_send_details():
    while True:
        try:
            name = input(
                "Enter the name of the person you want to transfer : ")
            amount = int(input("Enter the amount : "))
            break
        except ValueError:
            clear_screen()
            print("Invalid amount")
    clear_screen()
    return [name, amount]


# Stored Data
stored_admin_data = {"Hario": 12345}
stored_user_data = {"kisna": 12345,
                    "kaggleDhina": 123456, "dhina": 1234567, "har": 321}
available_denominations = {2000: 10, 1000: 20, 500: 30, 200: 40, 100: 50}
available_balances = {"kisna": 25000,
                      "kaggleDhina": 302000, "dhina": 300400, "har": 105000}
max_user_threshold = {"kisna": 0, "kaggleDhina": 0, "dhina": 0, "har": 0}

# Driver Code

clear_screen()
while True:
    print("ATM Application")
    user_choice = int(input("1. Admin Login\n2. User Login\n3. Exit\n"))
    if user_choice == 3:
        clear_screen()
        exit()

    if user_choice == 1:
        name, password = get_names(admin_login=True)
        if (authenticate_user(stored_admin_data, name, password, admin=True)):
            clear_screen()
            print("Welcome admin {}!".format(name))

            while True:
                admin_choice = int(
                    input("1. Add Money\n2. Check Balance\n3. Exit \n"))

                if admin_choice == 1:
                    denominations = get_money()
                    if validate_denominations(denominations, available_denominations):
                        print("Amount Updated")
                        available_denominations = update_money(
                            available_denominations, denominations)
                    else:
                        clear_screen()
                        print("Reached maximum threshold")
                    print(
                        "Available Balance : {}/-".format(find_balance(available_denominations)))

                if admin_choice == 2:
                    clear_screen()
                    print(
                        "Available Balance : {}/-".format(find_balance(available_denominations)))

                if admin_choice == 3:
                    clear_screen()
                    break

        else:
            # clear_screen()
            print("Invalid adminname or password")

    if user_choice == 2:
        name, password = get_names()
        if (authenticate_user(stored_user_data, name, password)):
            clear_screen()
            print("Welcome user {}!".format(name))
            while True:
                choice = int(input(
                    "1. Add Money\n2. Check Balance\n3. Update Pin\n4. Transfer money\n5. Withdraw money\n6. Exit\n"))

                if choice == 1:
                    denominations = get_money()
                    clear_screen()
                    print(
                        "Previous Balance : {}/-".format(available_balances[name]))
                    available_balances[name] = available_balances[name] + \
                        return_bal(denominations)
                    print(
                        "Amount to be Added : {}/-".format(return_bal(denominations)))
                    print(
                        "Your current balance is : {}/-".format(available_balances[name]))

                if choice == 2:
                    clear_screen()
                    print(
                        "Your current balance is : {}/-".format(available_balances[name]))

                if choice == 3:
                    clear_screen()
                    new_pin = int(input("Enter new pin: \n"))
                    stored_user_data = change_pin(
                        stored_user_data, name, new_pin)
                    print("PIN successfully updated")

                if choice == 4:
                    clear_screen()
                    transfer_name, amount = get_send_details()
                    if not validate_atm_funds(amount, available_denominations):
                        clear_screen()
                        print(
                            "Insufficient funds in atm. Sorry for the inconvenience")
                    else:
                        if validate_transfer(name, amount, available_balances, max_user_threshold):
                            if transfer_name in available_balances.keys():
                                print("Successfully transfered to {}".format(
                                    transfer_name))
                                available_balances = transfer_funds(
                                    name, transfer_name, amount, available_balances)
                                print("New Balance : {}".format(
                                    available_balances[name]))
                            else:
                                print("No such name as {}. Make sure that you've spelt it properly".format(
                                    transfer_name))

                        else:
                            clear_screen()
                            print("Insuffient Funds or maximum limit reached")
                            print("Your Balance : {}\nAmount : {} ".format(
                                available_balances[name], amount))
                            print("Total Amount transfered : {}".format(
                                max_user_threshold[name]))

                if choice == 5:
                    clear_screen()
                    while True:
                        try:
                            amount = int(
                                input("Enter the amount you want to withdraw : "))
                            break
                        except:
                            clear_screen()
                            print("Invalid amount")
                    if not validate_atm_funds(amount, available_denominations):
                        clear_screen()
                        print(
                            "Insufficient funds in atm. Sorry for the inconvenience")
                        continue
                    if available_balances[name] < amount:
                        print("Insufficient Funds")
                    else:
                        available_balances[name] -= amount
                        print("New Balance : {}".format(
                            available_balances[name]))

                if choice == 6:
                    clear_screen()
                    break

        else:
            clear_screen()
            print("Invalid username or password")
