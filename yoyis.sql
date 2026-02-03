-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 03, 2026 at 04:03 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `yoyis`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `contact_number` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `daily_sales`
--

CREATE TABLE `daily_sales` (
  `sales_id` int(11) NOT NULL,
  `sales_date` date DEFAULT NULL,
  `total_orders` int(11) DEFAULT NULL,
  `total_sales_amount` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `order_date` date DEFAULT NULL,
  `order_status` varchar(30) DEFAULT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_method` varchar(30) DEFAULT NULL,
  `payment_status` varchar(30) DEFAULT NULL,
  `amount_paid` decimal(10,2) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `stock_quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `category`, `size`, `price`, `stock_quantity`) VALUES
(101, 'Classic assorted bars', 'Bars', 'Box of 20', 245.00, 50),
(102, 'Premium assorted bars', 'Bars', 'Box of 20', 255.00, 50),
(103, 'Brownies', 'BARS', 'Box of 20', 260.00, 50),
(104, 'Butterscotch', 'Bars', 'Box of 20', 215.00, 50),
(105, 'Cassava bars', 'Bars', 'Box of 20', 230.00, 50),
(106, 'Cheese bars', 'Bars', 'Box of 20', 225.00, 50),
(107, 'Choco caramel bars', 'Bars', 'Box of 20', 210.00, 50),
(108, 'Dark fudge brownies', 'Bars', 'Box of 20', 320.00, 50),
(109, 'Revel bars', 'Bars', 'Box of 20', 255.00, 50),
(110, 'Food for the gods', 'Bars', 'Box of 20', 255.00, 50),
(111, 'Mango cake bars', 'Bars', 'Box of 20', 255.00, 50),
(112, 'Queen elizabeth bars', 'Bars', 'Box of 20', 255.00, 50),
(113, 'Black forest cake', 'Cake', 'Middie', 685.00, 25),
(114, 'Carrot cake', 'Cake', 'Cutie', 625.00, 25),
(115, 'Carrot cake', 'Cake', 'Middie', 685.00, 25),
(116, 'Classic chocolate cake', 'Cake', 'Petite', 345.00, 25),
(117, 'Classic chocolate cake', 'Cake', 'Cutie', 480.00, 25),
(118, 'Classic chocolate cake', 'Cake', 'Middie', 630.00, 25),
(119, 'Classic chocolate cake', 'Cake', 'Rectangle', 750.00, 25),
(120, 'Classic chocolate cake', 'Cake', 'Biggie', 905.00, 25),
(121, 'Custard cake', 'Cake', 'Cutie', 345.00, 25),
(122, 'Dulce de leche', 'Cake', 'Cutie', 450.00, 25),
(123, 'Dulce de leche', 'Cake', 'Middie', 720.00, 25),
(124, 'Heavenly mango cake', 'Cake', 'Cutie', 430.00, 25),
(125, 'Heavenly mango cake', 'Cake', 'Middie', 535.00, 25),
(126, 'Heavenly mango cake', 'Cake', 'Biggie', 685.00, 25),
(127, 'Heavenly mango cake', 'Cake', 'Rectangle', 715.00, 25),
(128, 'Mocha cake', 'Cake', 'Cutie', 405.00, 25),
(129, 'Mocha cake', 'Cake', 'Middie', 535.00, 25),
(130, 'Mocha cakeE', 'Cake', 'Biggie', 665.00, 25),
(131, 'Oreo cake', 'Cake', 'Middie', 535.00, 25),
(132, 'Queen elizabeth cake', 'Cake', 'Rectangle', 715.00, 25),
(133, 'Red velvet cake', 'Cake', 'Middie', 670.00, 25),
(134, 'Strawberries n cream cake', 'Cake', 'Middie', 635.00, 25),
(135, 'Tiramisu', 'Cake', 'Middie', 670.00, 25),
(136, 'Ube halaya cake', 'Cake', 'Cutie', 345.00, 25),
(137, 'Ube halaya cake', 'Cake', 'Middie', 535.00, 25),
(138, 'Vanilla cake', 'Cake', 'Cutie', 345.00, 25),
(139, 'Vanilla cake', 'Cake', 'Middie', 685.00, 25),
(140, 'Cheese sticks', 'Cookies', 'Pack of 8', 95.00, 25),
(141, 'Choco crinkles', 'Cookies', 'Box of 20', 275.00, 25),
(142, 'Oatmeal choco chip cookies', 'Cookies', 'Pack of 3', 95.00, 25),
(143, 'Macaroons', 'Cupcakes', 'Box of 20', 205.00, 50),
(144, 'Mini choco cupcakes', 'Cupcakes', 'Box of 20', 170.00, 50),
(145, 'Cheese cupcakes', 'Cupcakes', 'Box of 12', 210.00, 50),
(146, 'Choco marble cupcakes', 'Cupcakes', 'Box of 12', 220.00, 50),
(147, 'Puto cheese', 'Cupcakes', 'Pack of 4', 55.00, 50),
(148, 'Regular choco cupcakes', 'Cupcakes', 'Pack of 4', 200.00, 50),
(149, 'Special choco cupcakes', 'Cupcakes', 'Pack of 4', 400.00, 50),
(150, 'Torta cupcakes', 'Cupcakes', 'Pack of 4', 55.00, 50),
(151, 'Torta big', 'Cupcakes', 'Box of 10', 310.00, 50),
(152, 'Banana cake loaf', 'Loaves', 'Regular', 150.00, 20),
(153, 'Carrot cake loaf', 'Loaves', 'Regular', 245.00, 20),
(154, 'Blueberry cake loaf', 'Loaves', 'Regular', 245.00, 20),
(155, 'Walnut rum cake loaf', 'Loaves', 'Regular', 375.00, 20),
(156, 'Fruitcake', 'Loaves', 'Regular', 250.00, 20),
(157, 'Fruitcake', 'Loaves', 'Special', 325.00, 20),
(158, 'Buko pie', 'Pies', 'Regular', 245.00, 25),
(159, 'Buko pie', 'Pies', 'Small', 175.00, 25),
(160, 'Pineapple pie', 'Pies', 'Regular', 245.00, 25),
(161, 'Pineapple pie', 'Pies', 'Small', 175.00, 25),
(162, 'Brazo de mercedez', 'Rolls', 'Half', 280.00, 25),
(163, 'Brazo de mercedez', 'Rolls', 'Whole', 495.00, 25),
(164, 'Egg tarts', 'Tarts', 'Box of 8', 195.00, 25),
(165, 'Langka tarts', 'Tarts', 'Pack of 7', 95.00, 25),
(166, 'Masarica', 'Tarts', 'Pack of 10', 95.00, 25),
(167, 'Leche flan', 'Others', 'Middie', 195.00, 20),
(168, 'Crema de fruta', 'Others', 'Middie', 175.00, 20),
(169, 'Chocolate mousse', 'Others', 'Middie', 175.00, 20),
(170, 'Baked blueberry cheesecake', 'Others', 'Round 1/4', 175.00, 20),
(171, 'Baked blueberry cheesecake', 'Others', 'Round 1/2', 175.00, 20);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(50) DEFAULT NULL,
  `userpassword` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `userpassword`) VALUES
('admin', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `daily_sales`
--
ALTER TABLE `daily_sales`
  ADD PRIMARY KEY (`sales_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=172;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
