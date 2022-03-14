import java.text.SimpleDateFormat;
import java.time.LocalDate;
// import java.time.Period;
import java.util.*;

// admin = [ { email, password }, ... ]
// user = [ { email, password, uName, uId, walletAmount, msCard, [bookCart] }, ... ]
// books =[ { book_name, quantity, ISBN, book_cost, borrowedCount }, ... ]
// borrowedBooks = [ { ISBN, uId, uName, borrowedDate, returnDate, actualReturnDate, returnStatus, tenureCount }, ... ]
// fineDetails = [ { uId, uName, fineAmount, reason }, ... ]

public class LibraryManagement {
    private static Scanner sc;
    static Date Createdate = new Date();
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    static String CurrentDate;

    static void clear() {
        System.out.print("\033[H\033[2J");
    }

    static void setCurrentDate() {
        CurrentDate = String.valueOf(format.format(Createdate));
    }

    static String changeDate(String CD, int c) {
        String rtn = LocalDate
                .parse(CD)
                .plusDays(c)
                .toString();

        return rtn;
    }

    static void changeDateMenu() {
        clear();
        System.out.println("Enter No. of Days : ");
        int IncDays = sc.nextInt();
        CurrentDate = changeDate(CurrentDate, IncDays);
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            appMain();
        } catch (Exception e) {
        }
    }

    static void appMain() {
        clear();
        System.out.println("1.Admin Login");
        System.out.println("2.User Login");
        System.out.println("3.Change date");
        System.out.println("4.Exit");
        System.out.println("Current date: " + CurrentDate);

        int n = sc.nextInt();

        if (n == 1) {
            Admin.login();
        } else if (n == 2) {
            Borrower.login();
        } else if (n == 3) {
            changeDateMenu();
        } else if (n == 4) {
            System.exit(0);
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                appMain();
            } catch (Exception e) {
            }
        }

    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        // CREATING ADMIN CREDENTIALS
        Admin.adminDetails.add(new Admin("Admin", "123"));
        ArrayList<Integer> temp = new ArrayList<>();
        Borrower.borrowerDetails.add(new Borrower("user@gmail.com", "123", "hari", 0, 1500, "1000", temp));
        Admin.bookDetails.add(new Book("LifeOfPie", 3, 1, 10, 0));
        Admin.bookDetails.add(new Book("TempleOfSong", 3, 2, 20, 0));
        setCurrentDate();
        appMain();

    }

}

class Admin {

    static List<Admin> adminDetails = new ArrayList<Admin>(); // [{AdminEmail:"admin",AdminPassword:"123"},{...}]
    static List<Book> bookDetails = new ArrayList<>();

    static double lossFine = 50.0;
    static int MsLossFine = 10;
    static int PerdayFine = 2;

    public String AdminEmail;
    public String AdminPassword;

    Admin(String username, String password) {
        this.AdminEmail = username;
        this.AdminPassword = password;
    }

    static Scanner sc = new Scanner(System.in);

    static void login() {
        LibraryManagement.clear();

        System.out.println("**************** Admin Login ****************\n");
        System.out.println("Enter Admin Email : ");
        String email = sc.next();

        System.out.println("Enter Admin Password : ");
        String password = sc.next();

        // VALIDATE ADMIN CREDENTIALS
        for (int i = 0; i < adminDetails.size(); i++) {
            if (email.equals(adminDetails.get(i).AdminEmail) && password.equals(adminDetails.get(i).AdminPassword)) {
                adminMain();
            }
        }
        System.out.println("");
        System.out.println("Invalid Credentials");
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            LibraryManagement.appMain();
        } catch (Exception e) {
        }

    }

    static void adminMain() {
        LibraryManagement.clear();

        System.out.println("**************** Welcome to Admin ****************");
        System.out.println("1. Book Menu");
        System.out.println("2. Borrower Menu");
        System.out.println("3. Add Admin");
        System.out.println("4. Back");

        int n = sc.nextInt();

        if (n == 1) {
            bookMenu();
        } else if (n == 2) {
            borrowerMenu();
        } else if (n == 3) {
            addAdmin();
        } else if (n == 4) {
            LibraryManagement.appMain();
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                adminMain();
            } catch (Exception e) {
            }
        }
    }

    static void bookMenu() {
        LibraryManagement.clear();

        System.out.println("1. Add Book");
        System.out.println("2. Modify Book");
        System.out.println("3. Remove Book");
        System.out.println("4. View Books");
        System.out.println("5. Search Book");
        System.out.println("6. Book Borrower Details by ISBN");
        System.out.println("7. Book Reports");
        System.out.println("8. Back");

        int n = sc.nextInt();

        if (n == 1) {
            addBook();
        } else if (n == 2) {
            modifyBook();
        } else if (n == 3) {
            removeBook();
        } else if (n == 4) {
            viewBooks();
        } else if (n == 5) {
            searchBook();
        } else if (n == 6) {
            borrowerDetailByISBN();
        } else if (n == 7) {
            bookReports();
        } else if (n == 8) {
            adminMain();
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                bookMenu();
            } catch (Exception e) {
            }
        }

    }

    static void addBook() {
        LibraryManagement.clear();

        System.out.println("Enter Book Name : ");
        String Bname = sc.next();

        System.out.println("Enter Book Quantity : ");
        int Bquan = sc.nextInt();

        System.out.println("Enter Book ISBN Number : ");
        int Bisbn = sc.nextInt();

        System.out.println("Enter Book Cost : ");
        int Bcost = sc.nextInt();

        bookDetails.add(new Book(Bname, Bquan, Bisbn, Bcost, 0));

        System.out.println();
        System.out.println("Successfully Added the Book " + Bname);
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookMenu();
        } catch (Exception e) {
        }

    }

    static void modifyBook() {
        LibraryManagement.clear();

        System.out.println("Enter Book ISBN Number : ");
        int isbn = sc.nextInt();

        for (int i = 0; i < bookDetails.size(); i++) {
            if (isbn == bookDetails.get(i).BookISBN) {
                System.out.println("Book Name : " + bookDetails.get(i).BookName);
                System.out.println("Enter Quantity : ");
                bookDetails.get(i).BookQuantity = sc.nextInt();
                System.out.println("Successfully Modified Book Quantity!");
                System.out.println();
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                    bookMenu();
                } catch (Exception e) {
                }
            }
        }

        System.out.println();
        System.out.println("Invalid ISBN Number");
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookMenu();
        } catch (Exception e) {
        }

    }

    static void removeBook() {
        LibraryManagement.clear();

        System.out.println("Enter Book ISBN Number : ");
        int isbn = sc.nextInt();

        for (int i = 0; i < bookDetails.size(); i++) {
            if (isbn == bookDetails.get(i).BookISBN) {
                System.out.println("Book Name : " + bookDetails.get(i).BookName);
                bookDetails.remove(i);
                System.out.println("Successfully Removed The Book");
                System.out.println();
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                    bookMenu();
                } catch (Exception e) {
                }
            }
        }

        System.out.println();
        System.out.println("Invalid ISBN Number");
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookMenu();
        } catch (Exception e) {
        }
    }

    static void viewBooks() {
        LibraryManagement.clear();

        System.out.println("Order Book\n 1. Name or\n 2. Available Quantity");
        int viewBy = sc.nextInt();

        if (viewBy == 1) {
            Collections.sort(bookDetails, Book.bookNameComparator);
            System.out.printf("%-10s%-13s%-6s%-6s\n", "Name", "Quantity", "ISBN", "Cost");
            for (int i = 0; i < bookDetails.size(); i++) {
                System.out.printf("%-10s%-13d%-6d%-6d\n", bookDetails.get(i).BookName, bookDetails.get(i).BookQuantity,
                        bookDetails.get(i).BookISBN, bookDetails.get(i).BookCost);
            }

        } else if (viewBy == 2) {
            Collections.sort(bookDetails, Book.bookQenComparator);
            System.out.printf("%-10s%-13s%-6s%-6s\n", "Name", "Quantity", "ISBN", "Cost");
            for (int i = 0; i < bookDetails.size(); i++) {
                System.out.printf("%-10s%-13d%-6d%-6d\n", bookDetails.get(i).BookName, bookDetails.get(i).BookQuantity,
                        bookDetails.get(i).BookISBN, bookDetails.get(i).BookCost);
            }

        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                bookMenu();
            } catch (Exception e) {
            }
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookMenu();
        } catch (Exception e) {
        }
    }

    static void searchBook() {
        LibraryManagement.clear();

        System.out.println("Search By\n 1. Name or\n 2. ISBN Number");
        int viewBy = sc.nextInt();
        boolean found = false;
        if (viewBy == 1) {
            System.out.println("Enter Book Name(exact) : ");
            String value = sc.next();
            for (int i = 0; i < bookDetails.size(); i++) {
                if (value.equals(bookDetails.get(i).BookName)) {
                    found = true;
                    System.out.printf("%-10s%-13s%-6s%-6s\n", "Name", "Quantity", "ISBN", "Cost");
                    System.out.printf("%-10s%-13d%-6d%-6d\n", bookDetails.get(i).BookName,
                            bookDetails.get(i).BookQuantity,
                            bookDetails.get(i).BookISBN, bookDetails.get(i).BookCost);
                    System.out.println();
                    System.out.println("Press enter to continue...");
                    try {
                        System.in.read();
                        bookMenu();
                    } catch (Exception e) {
                    }
                }
            }
            if (!found) {
                System.out.println("Book not found");
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                    bookMenu();
                } catch (Exception e) {
                }
            }
        } else {
            System.out.println("Enter Book ISBN Number : ");
            int value = sc.nextInt();

            for (int i = 0; i < bookDetails.size(); i++) {
                if (value == bookDetails.get(i).BookISBN) {
                    found = true;
                    System.out.printf("%-10s%-13s%-6s%-6s\n", "Name", "Quantity", "ISBN", "Cost");
                    System.out.printf("%-10s%-13d%-6d%-6d\n", bookDetails.get(i).BookName,
                            bookDetails.get(i).BookQuantity,
                            bookDetails.get(i).BookISBN, bookDetails.get(i).BookCost);
                    System.out.println();
                    System.out.println("Press enter to continue...");
                    try {
                        System.in.read();
                        bookMenu();
                    } catch (Exception e) {
                    }
                }
            }
            if (!found) {
                System.out.println("Book not found");
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                    bookMenu();
                } catch (Exception e) {
                }
            }
        }
        System.out.println();
        System.out.println("Invalid Option");
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookMenu();
        } catch (Exception e) {
        }

    }

    static void bookReports() {
        LibraryManagement.clear();
        System.out.println("1. Less Borrowed Books");
        System.out.println("2. Mostly Borrowed Books");
        System.out.println("3. Not Borrowed Books");
        System.out.println("4. Not Retured On Date Details");
        System.out.println("5. Back");

        int n = sc.nextInt();

        if (n == 1) {
            lessBorrowedBooks();
        } else if (n == 2) {
            mostBorrowedBooks();
        } else if (n == 3) {
            notBorrowedBooks();
        } else if (n == 4) {
            notRetainedOnDateDetails();
        } else if (n == 5) {
            bookMenu();
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                bookMenu();
            } catch (Exception e) {
            }
        }

    }

    static void lessBorrowedBooks() {
        System.out.printf("%-10s%-13s%-6s%-6s%-13s\n", "Name", "Quantity", "ISBN", "Cost", "Borrowed Count");
        for (int i = 0; i < bookDetails.size(); i++) {
            if (bookDetails.get(i).BookBCount < 5) {
                System.out.printf("%-10s%-13d%-6d%-6d%-13d\n", bookDetails.get(i).BookName,
                        bookDetails.get(i).BookQuantity,
                        bookDetails.get(i).BookISBN, bookDetails.get(i).BookCost, bookDetails.get(i).BookBCount);
            }
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookReports();
        } catch (Exception e) {
        }

    }

    static void mostBorrowedBooks() {
        System.out.printf("%-10s%-13s%-6s%-6s%-13s\n", "Name", "Quantity", "ISBN", "Cost", "Borrowed Count");
        for (int i = 0; i < bookDetails.size(); i++) {
            if (bookDetails.get(i).BookBCount > 5) {
                System.out.printf("%-10s%-13d%-6d%-6d%-13d\n", bookDetails.get(i).BookName,
                        bookDetails.get(i).BookQuantity,
                        bookDetails.get(i).BookISBN, bookDetails.get(i).BookCost, bookDetails.get(i).BookBCount);
            }
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookReports();
        } catch (Exception e) {
        }
    }

    static void notBorrowedBooks() {
        System.out.printf("%-10s%-13s%-6s%-6s\n", "Name", "Quantity", "ISBN", "Cost");
        for (int i = 0; i < bookDetails.size(); i++) {
            if (bookDetails.get(i).BookBCount == 0) {
                System.out.printf("%-10s%-13d%-6d%-6d\n", bookDetails.get(i).BookName, bookDetails.get(i).BookQuantity,
                        bookDetails.get(i).BookISBN, bookDetails.get(i).BookCost);
            }
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookReports();
        } catch (Exception e) {
        }
    }

    static void notRetainedOnDateDetails() {
        LibraryManagement.clear();
        System.out.printf("%-10s%-6s%-16s%-16s%-16s%-15s\n", "Name", "ISBN", "Borrowed Date", "Expeted Return",
                "Returned Date", "Status");
        for (int i = 0; i < Borrower.borrowedDetails.size(); i++) {
            LocalDate d1 = LocalDate.parse(Borrower.borrowedDetails.get(i).Borrowed_DateReturn);
            LocalDate d2 = LocalDate.parse(LibraryManagement.CurrentDate);
            LocalDate d3 = LocalDate.parse(Borrower.borrowedDetails.get(i).Borrowed_DateActualReturn);
            int isDelay = d2.compareTo(d1);
            int isDelay1 = d1.compareTo(d3);
            if (isDelay < 0 || (isDelay <= 0 && Borrower.borrowedDetails.get(i).Borrowed_ReturnStatus == "Not Returned")
                    || isDelay1 < 0) {
                System.out.printf("%-10s%-6d%-16s%-16s%-16s%-15s\n", Borrower.borrowerDetails.get(
                        Borrower.borrowedDetails.get(i).Borrowed_BId).BName,
                        Borrower.borrowedDetails.get(i).Borrowed_ISBN,
                        Borrower.borrowedDetails.get(i).Borrowed_Date,
                        Borrower.borrowedDetails.get(i).Borrowed_DateReturn,
                        Borrower.borrowedDetails.get(i).Borrowed_DateActualReturn,
                        Borrower.borrowedDetails.get(i).Borrowed_ReturnStatus);
            }
        }
        System.out.println("\nPress enter to continue...");
        try {
            System.in.read();
            bookReports();
        } catch (Exception e) {
        }
    }

    static void borrowerDetailByISBN() {
        LibraryManagement.clear();

        System.out.println("Enter Book ISBN Number : ");
        int book = sc.nextInt();

        System.out.printf("%-10s%-6s%-16s%-16s%-16s%-15s\n", "Name", "ISBN", "Borrowed Date", "Expeted Return",
                "Returned Date", "Status");

        for (int i = 0; i < Borrower.borrowedDetails.size(); i++) {
            if (Borrower.borrowedDetails.get(i).Borrowed_ISBN == book) {
                System.out.printf("%-10s%-6d%-16s%-16s%-16s%-15s\n", Borrower.borrowerDetails.get(
                        Borrower.borrowedDetails.get(i).Borrowed_BId).BName,
                        Borrower.borrowedDetails.get(i).Borrowed_ISBN,
                        Borrower.borrowedDetails.get(i).Borrowed_Date,
                        Borrower.borrowedDetails.get(i).Borrowed_DateReturn,
                        Borrower.borrowedDetails.get(i).Borrowed_DateActualReturn,
                        Borrower.borrowedDetails.get(i).Borrowed_ReturnStatus);
            }
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookMenu();
        } catch (Exception e) {
        }
    }

    static void borrowerMenu() {
        LibraryManagement.clear();

        System.out.println("1. Add Borrower");
        System.out.println("2. Set Fine for Loss Book");
        System.out.println("3. Set Fine for MemberShip Card");
        System.out.println("4. Set Fine for Perday");
        System.out.println("5. Back");

        int n = sc.nextInt();

        if (n == 1) {
            addBorrower();
        } else if (n == 2) {
            setLossFine();
        } else if (n == 3) {
            setMsFine();
        } else if (n == 4) {
            setPerdayFine();
        } else if (n == 5) {
            adminMain();
        } else {
            System.out.println();
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                bookMenu();
            } catch (Exception e) {
            }
        }
    }

    static void addBorrower() {
        LibraryManagement.clear();

        System.out.println("Enter Borrower Name : ");
        String name = sc.next();
        System.out.println("Enter Borrower Email : ");
        String email = sc.next();
        System.out.println("Enter Borrower Password : ");
        String pass = sc.next();
        System.out.println("Enter Security Balance : ");
        int amt = sc.nextInt();

        int BId = Borrower.BorrowerIdGenerator;
        Borrower.BorrowerIdGenerator++;
        String msCard = String.valueOf(Borrower.MsCardIdGenerator);
        Borrower.MsCardIdGenerator++;
        ArrayList<Integer> cart = new ArrayList<>();
        Borrower.borrowerDetails.add(new Borrower(email, pass, name, BId, amt, msCard, cart));

        System.out.println();
        System.out.println("Borrower Added Successfully!");
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            borrowerMenu();
        } catch (Exception e) {
        }
    }

    static void setLossFine() {
        LibraryManagement.clear();
        System.out.println("Enter Fine for Loss Book (ex. 50.0): ");
        lossFine = sc.nextDouble();
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            borrowerMenu();
        } catch (Exception e) {
        }
    }

    static void setMsFine() {
        LibraryManagement.clear();
        System.out.println("Enter Fine for Loss of MemberShip Card : ");
        MsLossFine = sc.nextInt();
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            borrowerMenu();
        } catch (Exception e) {
        }
    }

    static void setPerdayFine() {
        LibraryManagement.clear();
        System.out.println("Enter Fine for Perday : ");
        PerdayFine = sc.nextInt();
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            borrowerMenu();
        } catch (Exception e) {
        }
    }

    static void addAdmin() {
        LibraryManagement.clear();
        System.out.println("Enter Admin Email : ");
        String adminEmail = sc.next();
        System.out.println("Enter Admin Password : ");
        String adminPassword = sc.next();

        adminDetails.add(new Admin(adminEmail, adminPassword));

        System.out.println();
        System.out.println("Admin Added Successfully!");
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            adminMain();
        } catch (Exception e) {
        }

    }
}

class Borrower {
    // user = [ { email, password, uName, uId, walletAmount, msCard, [bookCart] },
    // ... ]

    static Scanner sc = new Scanner(System.in);

    static List<Borrower> borrowerDetails = new ArrayList<>();
    static List<BorrowedBooks> borrowedDetails = new ArrayList<>();
    static List<FineDetails> fineDetails = new ArrayList<>();

    public static int CurrentUserId;
    static int BorrowerIdGenerator = 1;
    static int MsCardIdGenerator = 1001;

    public String Bemail;
    public String Bpassword;
    public String BName;
    public int BId;
    public int BWalletAmount;
    public String BMsCard;
    public ArrayList<Integer> BBookCart;

    Borrower(String email, String password, String BName, int BId, int BWalletAmount, String BMsCard,
            ArrayList<Integer> BookCart) {
        this.Bemail = email;
        this.Bpassword = password;
        this.BName = BName;
        this.BId = BId;
        this.BWalletAmount = BWalletAmount;
        this.BMsCard = BMsCard;
        this.BBookCart = BookCart;
    }

    static void login() {
        LibraryManagement.clear();
        System.out.println("**************** User Login ****************\n");
        System.out.println("Enter Your Email : ");
        String email = sc.next();

        System.out.println("Enter Your Password : ");
        String password = sc.next();

        // VALIDATE BORROWER CREDENTIALS
        for (int i = 0; i < borrowerDetails.size(); i++) {
            if (email.equals(borrowerDetails.get(i).Bemail) && password.equals(borrowerDetails.get(i).Bpassword)) {
                CurrentUserId = borrowerDetails.get(i).BId;
                borrowerMain();
            }
        }
        System.out.println("");
        System.out.println("Invalid Credentials");
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            LibraryManagement.appMain();
        } catch (Exception e) {
        }

    }

    static void borrowerMain() {
        LibraryManagement.clear();

        System.out.println("**************** Welcome to User Menu ****************");
        System.out.println("1. Book Menu");
        System.out.println("2. Cart Menu");
        System.out.println("3. Fine Details");
        System.out.println("4. Member Ship Card Lost");
        System.out.println("5. Logout");
        System.out.println("Wallet amount : " + borrowerDetails.get(CurrentUserId).BWalletAmount);

        int n = sc.nextInt();

        if (n == 1) {
            bookMenu_borrower();
        } else if (n == 2) {
            cartMenu();
        } else if (n == 3) {
            fineMenu();
        } else if (n == 4) {
            msCardLost();
        } else if (n == 5) {
            LibraryManagement.appMain();
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                borrowerMain();
            } catch (Exception e) {
            }
        }
    }

    static void msCardLost() {
        LibraryManagement.clear();
        System.out.println("Fine Amount : " + Admin.MsLossFine);
        System.out.println("Pay your fine from security amount or cash \n1.Security Balance\n2.cash");
        int option = sc.nextInt();
        if (option == 1) {
            if (borrowerDetails.get(CurrentUserId).BWalletAmount > Admin.MsLossFine) {
                borrowerDetails.get(CurrentUserId).BWalletAmount -= Admin.MsLossFine;
                System.out.println("Fine Paid!");
                fineDetails
                        .add(new FineDetails(CurrentUserId, borrowerDetails.get(CurrentUserId).BName, Admin.MsLossFine,
                                "MS Card Lost"));
                System.out.println("\nPress enter to continue");
                try {
                    System.in.read();
                    bookMenu_borrower();
                } catch (Exception e) {
                }
            } else {
                System.out.println("No Enough Available Balance to Pay\nPlease Pay through Cash ");
                fineDetails
                        .add(new FineDetails(CurrentUserId, borrowerDetails.get(CurrentUserId).BName, Admin.MsLossFine,
                                "MS Card Lost"));
                try {
                    System.in.read();
                    bookMenu_borrower();
                } catch (Exception e) {
                }
            }
        }
        System.out.println("\nPress enter to continue...");
        try {
            System.in.read();
            borrowerMain();
        } catch (Exception e) {
        }
    }

    static void fineMenu() {
        LibraryManagement.clear();
        System.out.printf("%-10s%-15s%-15s\n", "Name", "Amount", "Reason");
        for (int i = 0; i < fineDetails.size(); i++) {
            if (fineDetails.get(i).Fine_uId == CurrentUserId) {
                System.out.printf("%-10s%-15f%-15s\n", fineDetails.get(i).Fine_bName, fineDetails.get(i).Fine_Amount,
                        fineDetails.get(i).Fine_Reason);
            }
        }
        System.out.println("\nPress enter to continue...");
        try {
            System.in.read();
            borrowerMain();
        } catch (Exception e) {
        }
    }

    static void cartMenu() {
        LibraryManagement.clear();
        System.out.println("1. View Cart");
        System.out.println("2. Remove Book from Cart");
        System.out.println("3. Back");
        int n = sc.nextInt();

        if (n == 1) {
            viewcart();
        } else if (n == 2) {
            removeBookFromCart();
        } else if (n == 3) {
            borrowerMain();
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                cartMenu();
            } catch (Exception e) {
            }
        }
    }

    static void removeBookFromCart() {
        LibraryManagement.clear();
        System.out.println("Enter Book ISBN Number :");
        int isbn = sc.nextInt();
        if (borrowerDetails.get(CurrentUserId).BBookCart.contains(isbn)) {
            for (int i = 0; i < borrowerDetails.get(CurrentUserId).BBookCart.size(); i++) {
                if (borrowerDetails.get(CurrentUserId).BBookCart.get(i) == isbn)
                    borrowerDetails.get(CurrentUserId).BBookCart.remove(i);
            }
            System.out.println("\nBook Removed from Cart\nPress enter to continue...");
        } else {
            System.out.println("\nBook not in Cart\nPress enter to continue...");
        }
        try {
            System.in.read();
            cartMenu();
        } catch (Exception e) {
        }
    }

    static void viewcart() {
        LibraryManagement.clear();
        System.out.printf("%-10s%-6s\n", "Name", "ISBN");
        for (int i = 0; i < Admin.bookDetails.size(); i++) {
            if (borrowerDetails.get(CurrentUserId).BBookCart.contains(Admin.bookDetails.get(i).BookISBN)) {
                System.out.printf("%-10s%-6d\n", Admin.bookDetails.get(i).BookName,
                        Admin.bookDetails.get(i).BookISBN);
            }
        }
        System.out.println("\nPress enter to continue...");
        try {
            System.in.read();
            cartMenu();
        } catch (Exception e) {
        }
    }

    static void bookMenu_borrower() {
        LibraryManagement.clear();

        System.out.println("1. View Books");
        System.out.println("2. Add Books to cart");
        System.out.println("3. Borrow Books");
        System.out.println("4. Borrowed History");
        System.out.println("5. Return Book");
        System.out.println("6. Extend Book Return Date");
        System.out.println("7. Back");

        int n = sc.nextInt();

        if (n == 1) {
            viewBooks_borrower();
        } else if (n == 2) {
            addBookstCart();
        } else if (n == 3) {
            borrowBooks();
        } else if (n == 4) {
            borrowedHistory();
        } else if (n == 5) {
            returnBookMenu();
        } else if (n == 6) {
            extendDate();
        } else if (n == 7) {
            borrowerMain();
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                bookMenu_borrower();
            } catch (Exception e) {
            }
        }
    }

    static void extendDate() {
        LibraryManagement.clear();

        System.out.println("Enter Book ISBN : ");
        int isbn = sc.nextInt();

        boolean flag = false;
        int bookBorrowedId = 0;
        for (int i = 0; i < borrowedDetails.size(); i++) {
            if (borrowedDetails.get(i).Borrowed_BId == CurrentUserId && borrowedDetails.get(i).Borrowed_ISBN == isbn) {
                flag = true;
                bookBorrowedId = borrowedDetails.get(i).Borrowed_Id;

            }
        }

        if (flag) {
            if (borrowedDetails.get(bookBorrowedId).Borrowed_TenCount < 2) {
                System.out.println("Enter Date (yyyy-mm-dd)(with hyphen) : ");
                String d = sc.next();
                borrowedDetails.get(bookBorrowedId).Borrowed_DateReturn = d;
                borrowedDetails.get(bookBorrowedId).Borrowed_TenCount++;
                System.out.println("\nDate extended");
            } else {
                System.out.println("\nYou have reached max changes!");
            }

            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                bookMenu_borrower();
            } catch (Exception e) {
            }
        } else {
            System.out.println("\n you have not borrowed the book\nPress enter to continue...");
            try {
                System.in.read();
                bookMenu_borrower();
            } catch (Exception e) {
            }
        }

    }

    static void returnBookMenu() {
        LibraryManagement.clear();

        System.out.println("1. Return The Book");
        System.out.println("2. Book Lost");
        System.out.println("3. Back");
        int n = sc.nextInt();

        if (n == 1) {
            returnBook();
        } else if (n == 2) {
            bookLost();
        } else if (n == 3) {
            bookMenu_borrower();
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                bookMenu_borrower();
            } catch (Exception e) {
            }
        }
    }

    static void bookLost() {
        LibraryManagement.clear();
        System.out.println("Enter Book ISBN Number :");
        int n = sc.nextInt();

        LibraryManagement.clear();
        boolean flag = false;
        int bookBorrowedId = 0;
        for (int i = 0; i < borrowedDetails.size(); i++) {
            if (borrowedDetails.get(i).Borrowed_BId == CurrentUserId && borrowedDetails.get(i).Borrowed_ISBN == n) {
                flag = true;
                bookBorrowedId = borrowedDetails.get(i).Borrowed_Id;

            }
        }

        if (flag) {

            int bookcost = 0;
            for (int i = 0; i < Admin.bookDetails.size(); i++) {
                if (Admin.bookDetails.get(i).BookISBN == n) {
                    bookcost = Admin.bookDetails.get(i).BookCost;
                }
            }

            double fine = bookcost * (Admin.lossFine / 100);
            // double fine = bookcost * fineper;
            borrowedDetails.get(bookBorrowedId).Borrowed_DateActualReturn = "---";
            borrowedDetails.get(bookBorrowedId).Borrowed_ReturnStatus = "Book Lost";
            System.out.println("Fine for Loss of Book : " + fine);
            System.out.println("Pay your fine from security amount or cash \n1.Security Balance\n2.cash");
            int option = sc.nextInt();
            if (option == 1) {
                if (borrowerDetails.get(CurrentUserId).BWalletAmount > fine) {
                    borrowerDetails.get(CurrentUserId).BWalletAmount -= fine;
                    System.out.println("Fine Paid!");
                    fineDetails.add(new FineDetails(CurrentUserId, borrowerDetails.get(CurrentUserId).BName, fine,
                            "Book Lost"));
                    System.out.println("\nPress enter to continue");
                    try {
                        System.in.read();
                        returnBookMenu();
                    } catch (Exception e) {
                    }
                } else {
                    System.out.println("No Enough Available Balance to Pay\nPlease Pay through Cash ");
                    fineDetails.add(new FineDetails(CurrentUserId, borrowerDetails.get(CurrentUserId).BName, fine,
                            "Book Lost"));
                    try {
                        System.in.read();
                        returnBookMenu();
                    } catch (Exception e) {
                    }
                }
            } else {
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                    returnBookMenu();
                } catch (Exception e) {
                }
            }
        } else {
            System.out.println("\n you have not borrowed the book\nPress enter to continue...");
            try {
                System.in.read();
                returnBookMenu();
            } catch (Exception e) {
            }
        }

    }

    static void returnBook() {
        LibraryManagement.clear();

        System.out.println("Enter Book ISBN Number : ");
        int isbn = sc.nextInt();
        LibraryManagement.clear();
        int bookBorrowedId = 0;
        boolean flag = false;
        System.out.printf("%-6s%-15s%-15s%-13s%-15s\n", "ISBN", "Borrowed Date", "Return Date", "Status",
                "Tenure Count");
        for (int i = 0; i < borrowedDetails.size(); i++) {
            if (borrowedDetails.get(i).Borrowed_BId == CurrentUserId && borrowedDetails.get(i).Borrowed_ISBN == isbn) {
                flag = true;
                System.out.printf("%-6d%-15s%-15s%-13s%-15d\n", borrowedDetails.get(i).Borrowed_ISBN,
                        borrowedDetails.get(i).Borrowed_Date, borrowedDetails.get(i).Borrowed_DateReturn,
                        borrowedDetails.get(i).Borrowed_ReturnStatus, borrowedDetails.get(i).Borrowed_TenCount);
                bookBorrowedId = borrowedDetails.get(i).Borrowed_Id;
            }
        }

        if (flag) {
            System.out.println("\nReturn Transaction Success, Thank You!");

            borrowedDetails.get(bookBorrowedId).Borrowed_DateActualReturn = LibraryManagement.CurrentDate;
            borrowedDetails.get(bookBorrowedId).Borrowed_ReturnStatus = "Returned";
            for (int x = 0; x < Admin.bookDetails.size(); x++) {
                if (isbn == Admin.bookDetails.get(x).BookISBN) {
                    Admin.bookDetails.get(x).BookQuantity++;
                }
            }
            String date1 = borrowedDetails.get(bookBorrowedId).Borrowed_Date;
            String date2 = borrowedDetails.get(bookBorrowedId).Borrowed_DateActualReturn;
            int DaysBetween = 0;
            while (!date1.equals(date2)) {
                DaysBetween += 1;
                date1 = LibraryManagement.changeDate(date1, 1);
            }

            System.out.println("Days Between : " + DaysBetween);
            if (DaysBetween > 15) {
                int fineDays = DaysBetween - 15;
                int fine = 0;
                System.out.println("fineDays = " + fineDays);
                double pd_fineAmount = Admin.PerdayFine;
                while (fineDays > 0) {
                    int c = 0;
                    while (c < 10 && fineDays > 0) {
                        fine += pd_fineAmount;
                        fineDays--;
                        c++;
                    }
                    pd_fineAmount = Math.pow(pd_fineAmount, 2);
                }
                System.out.println("your have fine for taking more then 15 days to return the book");
                System.out.println("your fine amount is : " + fine);
                System.out.println("Pay your fine from security amount or cash \n1.Security Balance\n2.cash");
                int option = sc.nextInt();
                if (option == 1) {
                    if (borrowerDetails.get(CurrentUserId).BWalletAmount > fine) {
                        borrowerDetails.get(CurrentUserId).BWalletAmount -= fine;
                        System.out.println("Fine Paid!");
                        fineDetails.add(new FineDetails(CurrentUserId, borrowerDetails.get(CurrentUserId).BName, fine,
                                "Late Return"));
                        System.out.println("\nPress enter to continue");
                        try {
                            System.in.read();
                            returnBookMenu();
                        } catch (Exception e) {
                        }
                    } else {
                        System.out.println("No Enough Available Balance to Pay\nPlease Pay through Cash ");
                        fineDetails.add(new FineDetails(CurrentUserId, borrowerDetails.get(CurrentUserId).BName, fine,
                                "Late Return"));
                        try {
                            System.in.read();
                            returnBookMenu();
                        } catch (Exception e) {
                        }
                    }
                } else {
                    System.out.println("Press enter to continue...");
                    try {
                        System.in.read();
                        returnBookMenu();
                    } catch (Exception e) {
                    }
                }

            } else {
                System.out.println("Thank you!");
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                    returnBookMenu();
                } catch (Exception e) {
                }
            }

        } else {
            System.out.println("\n you have not borrowed the book\nPress enter to continue...");
            try {
                System.in.read();
                returnBookMenu();
            } catch (Exception e) {
            }
        }

    }

    static void viewBooks_borrower() {
        LibraryManagement.clear();
        System.out.printf("%-10s%-13s%-6s\n", "Name", "Quantity", "ISBN");
        for (int i = 0; i < Admin.bookDetails.size(); i++) {
            System.out.printf("%-10s%-13d%-6d\n", Admin.bookDetails.get(i).BookName,
                    Admin.bookDetails.get(i).BookQuantity,
                    Admin.bookDetails.get(i).BookISBN);
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookMenu_borrower();
        } catch (Exception e) {
        }

    }

    static void addBookstCart() {
        LibraryManagement.clear();

        System.out.printf("%-10s%-13s%-6s\n", "Name", "Quantity", "ISBN");
        for (int i = 0; i < Admin.bookDetails.size(); i++) {
            System.out.printf("%-10s%-13d%-6d\n", Admin.bookDetails.get(i).BookName,
                    Admin.bookDetails.get(i).BookQuantity,
                    Admin.bookDetails.get(i).BookISBN);
        }
        System.out.println("Enter No. of Books to Add (Maximum 3) : ");
        int no = sc.nextInt();
        if (no < 4) {
            for (int j = 0; j < no; j++) {
                LibraryManagement.clear();
                if (borrowerDetails.get(CurrentUserId).BBookCart.size() < 3) {
                    System.out.println("Enter Book ISBN Number : ");
                    int n = sc.nextInt();
                    boolean flag = true;
                    for (int k = 0; k < Admin.bookDetails.size(); k++) {
                        if (Admin.bookDetails.get(k).BookISBN == n) {
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) {
                        if (borrowerDetails.get(CurrentUserId).BBookCart.size() == 0) {
                            borrowerDetails.get(CurrentUserId).BBookCart.add(n);
                            System.out.println();
                            System.out.println("Book Added to the cart!");
                            System.out.println("Press enter to continue...");
                            try {
                                System.in.read();
                            } catch (Exception e) {
                            }

                        } else {
                            if (borrowerDetails.get(CurrentUserId).BBookCart.contains(n)) {
                                System.out.println();
                                System.out.println("Book Already in the cart");
                                try {
                                    System.in.read();
                                } catch (Exception e) {
                                }
                                continue;
                            } else {
                                borrowerDetails.get(CurrentUserId).BBookCart.add(n);
                                System.out.println();
                                System.out.println("Book Added to the cart!");
                                System.out.println("Press enter to continue...");
                                try {
                                    System.in.read();
                                } catch (Exception e) {
                                }
                                continue;
                            }

                        }
                    } else {
                        System.out.println("Book ISBN Not Found");
                        System.out.println("Press enter to continue...");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                        }
                        continue;
                    }
                } else {
                    System.out.println("You Reached Maximum Cart Limit");
                    System.out.println("Press enter to continue...");
                    try {
                        System.in.read();
                        bookMenu_borrower();
                    } catch (Exception e) {
                    }
                    continue;
                }
            }
        } else {
            System.out.println("Maximum 3 books only allowed");
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
            } catch (Exception e) {
            }
        }
        bookMenu_borrower();

    }

    static void borrowBooks() {
        LibraryManagement.clear();
        System.out.printf("%-10s%-6s\n", "Name", "ISBN");
        for (int i = 0; i < Admin.bookDetails.size(); i++) {
            if (borrowerDetails.get(CurrentUserId).BBookCart.contains(Admin.bookDetails.get(i).BookISBN)) {
                System.out.printf("%-10s%-6d\n", Admin.bookDetails.get(i).BookName,
                        Admin.bookDetails.get(i).BookISBN);
            }
        }

        System.out.println("Do You Want to Checkout (y/n) : ");
        String n = sc.next();
        if (n.equals("y")) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (int i = 0; i < borrowerDetails.get(CurrentUserId).BBookCart.size(); i++) {
                LibraryManagement.clear();
                boolean flag = true;
                for (int j = 0; j < borrowedDetails.size(); j++) {
                    if (borrowerDetails.get(CurrentUserId).BBookCart.get(i) == borrowedDetails.get(j).Borrowed_ISBN) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    if (borrowerDetails.get(CurrentUserId).BWalletAmount >= 500) {
                        System.out.println("\nBook ISBN : " + borrowerDetails.get(CurrentUserId).BBookCart.get(i));
                        System.out.println("Enter Return Date (yyyy-mm-dd)(With hyphen) : ");
                        String date = sc.next();
                        // { ISBN, uId, borrowedDate, returnDate, actualReturnDate, returnStatus,
                        // tenureCount }
                        borrowedDetails.add(new BorrowedBooks(BorrowedBooks.BorrowedIdGendrator,
                                borrowerDetails.get(CurrentUserId).BBookCart.get(i),
                                CurrentUserId, LibraryManagement.CurrentDate, date, "0000/00/00", "Not Returned", 0));
                        BorrowedBooks.BorrowedIdGendrator++;
                        for (int x = 0; x < Admin.bookDetails.size(); x++) {
                            if (Admin.bookDetails.get(x).BookISBN == borrowerDetails.get(CurrentUserId).BBookCart
                                    .get(i)) {
                                Admin.bookDetails.get(x).BookBCount++;
                                Admin.bookDetails.get(x).BookQuantity--;
                            }
                        }
                        temp.add(borrowerDetails.get(CurrentUserId).BBookCart.get(i));
                        continue;
                    } else {
                        System.out.println(
                                "\nYour Security Balance is Under Minimal Limit\nSorry you cannot Borrow\nPleace increase your security balance");
                        try {
                            System.in.read();
                            bookMenu_borrower();
                        } catch (Exception e) {
                        }
                    }
                } else {
                    System.out.println("Sorry, You Have Already Borrowed The Book");
                    try {
                        System.in.read();
                    } catch (Exception e) {
                    }
                    continue;
                }
            }
            for (int i = 0; i < temp.size(); i++) {
                for (int j = 0; j < borrowerDetails.get(CurrentUserId).BBookCart.size(); j++) {
                    if (borrowerDetails.get(CurrentUserId).BBookCart.get(j).equals(temp.get(i))) {
                        borrowerDetails.get(CurrentUserId).BBookCart.remove(j);
                    }
                }
            }
            System.out.println();
            System.out.println("Done! enter to continue...");
            try {
                System.in.read();
                bookMenu_borrower();
            } catch (Exception e) {
            }

        } else {
            bookMenu_borrower();
        }

    }

    static void borrowedHistory() {
        LibraryManagement.clear();
        System.out.println("**************** Borrowed History ****************");
        System.out.printf("%-6s%-15s%-15s%-13s%-15s\n", "ISBN", "Borrowed Date", "Return Date", "Status",
                "Tenure Count");
        for (int i = 0; i < borrowedDetails.size(); i++) {
            if (borrowedDetails.get(i).Borrowed_BId == CurrentUserId) {
                System.out.printf("%-6d%-15s%-15s%-13s%-15d\n", borrowedDetails.get(i).Borrowed_ISBN,
                        borrowedDetails.get(i).Borrowed_Date, borrowedDetails.get(i).Borrowed_DateActualReturn,
                        borrowedDetails.get(i).Borrowed_ReturnStatus, borrowedDetails.get(i).Borrowed_TenCount);
            }
        }

        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            bookMenu_borrower();
        } catch (Exception e) {
        }
    }
}

class Book {

    // books =[ { book_name, quantity, ISBN, book_cost, borrowedCount }, ... ]

    public String BookName;
    public int BookQuantity;
    public int BookISBN;
    public int BookCost;
    public int BookBCount;

    Book(String book_name, int book_quantity, int book_isbn, int book_cost, int book_bcount) {
        this.BookName = book_name;
        this.BookQuantity = book_quantity;
        this.BookISBN = book_isbn;
        this.BookCost = book_cost;
        this.BookBCount = book_bcount;
    }

    public String getBookName() {
        return this.BookName;
    }

    public int getBookQan() {
        return this.BookQuantity;
    }

    // Comparator for sorting the list by Name
    public static Comparator<Book> bookNameComparator = new Comparator<Book>() {
        public int compare(Book s1, Book s2) {
            String BookQen1 = s1.getBookName().toUpperCase();
            String BookQen2 = s2.getBookName().toUpperCase();
            // ascending order
            return BookQen1.compareTo(BookQen2);
        }
    };

    // Comparator for sorting the list by Quantity
    public static Comparator<Book> bookQenComparator = new Comparator<Book>() {
        public int compare(Book s1, Book s2) {

            int rollno1 = s1.getBookQan();
            int rollno2 = s2.getBookQan();

            return rollno2 - rollno1;
        }
    };

}

class BorrowedBooks {
    // borrowedBooks = [ { ISBN, uId, borrowedDate, returnDate, actualReturnDate,
    // returnStatus, tenureCount }, ... ]
    static int BorrowedIdGendrator = 0;

    public int Borrowed_Id;
    public int Borrowed_ISBN;
    public int Borrowed_BId;
    public String Borrowed_Date;
    public String Borrowed_DateReturn;
    public String Borrowed_DateActualReturn;
    public String Borrowed_ReturnStatus;
    public int Borrowed_TenCount;

    BorrowedBooks(int Borrowed_Id, int Borrowed_ISBN, int Borrowed_BId, String Borrowed_Date,
            String Borrowed_DateReturn,
            String Borrowed_DateActualReturn, String Borrowed_ReturnStatus, int Borrowed_TenCount) {
        this.Borrowed_Id = Borrowed_Id;
        this.Borrowed_ISBN = Borrowed_ISBN;
        this.Borrowed_BId = Borrowed_BId;
        this.Borrowed_Date = Borrowed_Date;
        this.Borrowed_DateReturn = Borrowed_DateReturn;
        this.Borrowed_DateActualReturn = Borrowed_DateActualReturn;
        this.Borrowed_ReturnStatus = Borrowed_ReturnStatus;
        this.Borrowed_TenCount = Borrowed_TenCount;
    }

}

class FineDetails {
    // fineDetails = [ { uId, uName, fineAmount, reason }, ... ]
    public int Fine_uId;
    public String Fine_bName;
    public double Fine_Amount;
    public String Fine_Reason;

    FineDetails(int uId, String uName, double fineAmount, String reason) {
        this.Fine_uId = uId;
        this.Fine_bName = uName;
        this.Fine_Amount = fineAmount;
        this.Fine_Reason = reason;
    }

}