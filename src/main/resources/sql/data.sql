insert into books (amount, author, title) values
    (10, 'George Orwell', '1984'),
    (15, 'Aldous Huxley', 'Brave New World'),
    (8, 'J.R.R. Tolkien', 'The Hobbit'),
    (12, 'J.K. Rowling', 'Harry Potter and the Philosopher''s Stone'),
    (7, 'F. Scott Fitzgerald', 'The Great Gatsby'),
    (5, 'Jane Austen', 'Pride and Prejudice'),
    (9, 'Leo Tolstoy', 'War and Peace'),
    (11, 'Gabriel Garcia Marquez', 'One Hundred Years of Solitude'),
    (6, 'Mark Twain', 'Adventures of Huckleberry Finn'),
    (4, 'Harper Lee', 'To Kill a Mockingbird');

insert into members (membership_date, name) values
    ('2024-01-01 10:00:00', 'John Doe'),
    ('2024-02-15 15:30:00', 'Jane Smith');

insert into memb_books (book_id, member_id) values
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 2),
    (5, 1),
    (6, 2);
