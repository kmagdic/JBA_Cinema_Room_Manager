package cinema;

import java.util.Scanner;

public class Cinema {

    static boolean withPrice = false;

    static class Seat {
        boolean occupied = false;
        int price = 0;

        public Seat(boolean occupied, int price) {
            this.occupied = occupied;
            this.price = price;
        }

        String print(boolean withPrice) {
            if(withPrice) {
                return String.format("%c (%02d)", (occupied == true) ? 'B' : 'S', price);
            } else
                return String.format("%c", (occupied == true) ? 'B' : 'S');
        }
    }

    static int noOfRows;
    static int noOfSeats;
    static Seat[][] seats;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of rows: \n> ");
        noOfRows = scanner.nextInt();
        System.out.print("Enter the number of seats in each row: \n> ");
        noOfSeats = scanner.nextInt();

        int income = 0;
        if (noOfRows * noOfSeats <= 60) {
            income = noOfRows * noOfSeats * 10;
        } else {
            income = (noOfRows / 2) * noOfSeats * 10 +
                    (noOfRows - (noOfRows / 2)) * noOfSeats * 8;
        }

        // System.out.println("Total income: \n$" + income);

        //
        // initialize seats
        //
        seats = new Seat[noOfRows][];
        for(int currRow = 0; currRow < noOfRows; currRow++) {
            seats[currRow] = new Seat[noOfSeats];
            for(int currSeat = 0; currSeat < noOfSeats; currSeat++) {
                int price = 0;
                if (noOfRows * noOfSeats > 60 && currRow >= noOfRows / 2) {
                    price = 8;
                } else {
                    price = 10;
                }

                seats[currRow][currSeat] = new Seat(false, price);
            }
        }

        while (true) {
            System.out.println();
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.print("0. Exit\n> ");
            int menu = scanner.nextInt();

            if(menu == 1) {
                printCinemaRoom(withPrice);
            } else if(menu == 2) {
                buyTicket(scanner);
            } else if(menu == 3) {
                statistics();
            } else if (menu == 0) {
                break;
            }
        }

    }

    private static void buyTicket(Scanner scanner) {
        int reserveRow, reserveSeat;

        while(true) {
            System.out.print("\nEnter a row number: \n> ");
            reserveRow = scanner.nextInt() - 1;
            System.out.print("Enter a seat number in that row: \n> ");
            reserveSeat = scanner.nextInt() - 1;

            if(reserveRow < 0 || reserveRow >= noOfRows ||
                    reserveSeat < 0 || reserveSeat >= noOfSeats) {
                System.out.println("\nWrong input!");
                continue;
            }

            if(seats[reserveRow][reserveSeat].occupied == true) {
                System.out.println("\nThat ticket has already been purchased!");
                continue;
            } else {
                break;
            }
        }

        seats[reserveRow][reserveSeat].occupied = true;

        System.out.println("Ticket price: $" + seats[reserveRow][reserveSeat].price);
    }

    private static void statistics() {
        int ticketsPurchased = 0;
        int seatsTotal = 0;
        int currentIncome = 0;
        int totalIncome = 0;
        for(int currRow = 0; currRow < noOfRows; currRow++) {
            for (int currSeat = 0; currSeat < noOfSeats; currSeat++) {
                seatsTotal ++;
                totalIncome += seats[currRow][currSeat].price;
                if(seats[currRow][currSeat].occupied) {
                    ticketsPurchased++;
                    currentIncome += seats[currRow][currSeat].price;
                }

            }
        }

        System.out.println("Number of purchased tickets: " + ticketsPurchased);
        System.out.printf("Percentage: %.2f%%\n", ((float) ticketsPurchased /  seatsTotal) * 100);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    private static void printCinemaRoom(boolean withPrice) {
        System.out.print("\nCinema:\n ");
        for(int i = 0; i < noOfSeats; i++) {
            System.out.printf(" %d", (i + 1) );
            if(withPrice)
                System.out.printf(" (%02d)", 0 );
        }
        System.out.println("");

        for(int currRow = 0; currRow < noOfRows; currRow++) {

            // print row number
            System.out.print((currRow + 1) + " ");

            // print seats
            for(int currSeat = 0; currSeat < noOfSeats; currSeat++) {
                System.out.print(seats[currRow][currSeat].print(withPrice) + " ");
            }
            System.out.println();
        }
    }
}