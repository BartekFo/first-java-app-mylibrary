package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookID";

    private TextView txtBookName, txtAuthor, txtPages, txtDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        Intent intent = getIntent();
        if(null != intent) {
            int bookID = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookID != -1) {
                Book incomingBook = Utils.getInstance(this).getBookByID(bookID);
                if(null != incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleFavoriteBooks(Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInFavoriteBooks = false;

        for (Book b: favoriteBooks) {
            if(b.getId() == book.getId()){
                existInFavoriteBooks = true;
            }

        }

        if (existInFavoriteBooks) {
            btnAddToFavorite.setEnabled(false);
        } else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToFavorite(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, FavoriteActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrentlyReadingBooks(Book book) {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existInCurrentlyReadingBooks = false;

        for (Book b: currentlyReadingBooks) {
            if(b.getId() == book.getId()){
                existInCurrentlyReadingBooks = true;
            }

        }

        if (existInCurrentlyReadingBooks) {
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;

        for (Book b: wantToReadBooks) {
            if(b.getId() == book.getId()){
                existInWantToReadBooks = true;
            }

        }

        if (existInWantToReadBooks) {
            btnAddToWantToRead.setEnabled(false);
        } else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Enable and Disable button,
     * Add the book to Already Read Book ArrayList
     * @param book
     */

    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existInAlreadyReadBooks = false;

        for (Book b: alreadyReadBooks) {
            if(b.getId() == book.getId()){
                existInAlreadyReadBooks = true;
            }

        }

        if (existInAlreadyReadBooks) {
            btnAddToAlreadyRead.setEnabled(false);
        } else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);
    }

    private void initViews() {
        txtAuthor = findViewById(R.id.txtDetailsAuthorValue);
        txtBookName = findViewById(R.id.txtDetailBookNameValue);
        txtPages = findViewById(R.id.txtDetailsPageValue);
        txtDescription = findViewById(R.id.txtDetailsLongDescription);

        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorites);
        btnAddToWantToRead = findViewById(R.id.btnAddToWishlist);

        bookImage = findViewById(R.id.imgBookDetails);
    }
}