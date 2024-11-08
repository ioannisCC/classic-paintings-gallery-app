package HelperClasses.HomeAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ioannis.unipi.example.assignment1.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtworksDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "artworks.db";
    private static final int DATABASE_VERSION = 1;

    // singleton pattern to have the same database across the whole app
    private static ArtworksDatabase instance;

    private ArtworksDatabase(@Nullable Context context) {
        super(Objects.requireNonNull(context).getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase(); // get the database instance
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        String path = dbFile.getAbsolutePath();
        Log.d("Database Path", path);
    }

    public static synchronized ArtworksDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new ArtworksDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE artworks" +
                    "(id INTEGER PRIMARY KEY autoincrement," +
                    "title Text DEFAULT 'Unknown'," +
                    "description Text DEFAULT 'Unknown'," +
                    "image_res_id integer DEFAULT -1," +
                    "artist Text DEFAULT 'Unknown'," +
                    "year Text DEFAULT 'Unknown'," +
                    "medium Text DEFAULT 'Unknown'," +
                    "dimensions Text DEFAULT 'Unknown'," +
                    "art_movement Text DEFAULT 'Unknown'," +
                    "location Text DEFAULT 'Unknown')");
        }
        catch (Exception e) {
            Log.e("DatabaseError", "Error creating table: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS artworks");
            onCreate(db);
        }
        catch (Exception e) {
            Log.e("DatabaseError", "Error upgrading database: " + e.getMessage());
        }
    }

    public void addArtwork(Artwork artwork, Context context) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            if (artwork!=null) {
                ContentValues values = getContentValues(artwork);
                db.insert("artworks", null, values);
            }
            else {
                Log.d("from addArtwork", "artwork is Null");
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error inserting artwork: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // gather all the values that will be added together
    @NonNull
    private static ContentValues getContentValues(Artwork artwork) {
        ContentValues values = new ContentValues();

        values.put("image_res_id", artwork.getImageResId());
        values.put("title", artwork.getTitle());
        values.put("description", artwork.getDescription());
        values.put("artist", artwork.getArtist());
        values.put("year", artwork.getYear());
        values.put("medium", artwork.getMedium());
        values.put("dimensions", artwork.getDimensions());
        values.put("art_movement", artwork.getArtMovement());
        values.put("location", artwork.getLocation());

        return values;
    }

    public List<Artwork> getAllArtworks(Context context) {
        List<Artwork> artworks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM artworks", null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    int resId = cursor.getInt(cursor.getColumnIndexOrThrow("image_res_id"));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow("artist"));
                    String year = cursor.getString(cursor.getColumnIndexOrThrow("year"));
                    String medium = cursor.getString(cursor.getColumnIndexOrThrow("medium"));
                    String dimensions = cursor.getString(cursor.getColumnIndexOrThrow("dimensions"));
                    String artMovement = cursor.getString(cursor.getColumnIndexOrThrow("art_movement"));
                    String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));

                    Artwork artwork = new Artwork(resId, title, description, artist, year,
                            medium, dimensions, artMovement, location);
                    artworks.add(artwork);
                } catch (Exception e) {
                    Log.e("DatabaseError", "Column not found: " + e.getMessage());
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return artworks;
    }

    // retrieve artwork by title, artist
    public Artwork getArtwork(String title, String artist, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        Artwork artwork = null;

        try (Cursor cursor = db.rawQuery("SELECT * FROM artworks WHERE title = ? AND artist = ?", new String[]{title, artist})) {

            if (cursor != null && cursor.moveToFirst()) {
                artwork = new Artwork(-1, null, null, null,
                        null, null, null, null, null);

                artwork.setImageResId(cursor.getInt(cursor.getColumnIndexOrThrow("image_res_id")));
                artwork.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                artwork.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                artwork.setArtist(cursor.getString(cursor.getColumnIndexOrThrow("artist")));
                artwork.setYear(cursor.getString(cursor.getColumnIndexOrThrow("year")));
                artwork.setMedium(cursor.getString(cursor.getColumnIndexOrThrow("medium")));
                artwork.setDimensions(cursor.getString(cursor.getColumnIndexOrThrow("dimensions")));
                artwork.setArtMovement(cursor.getString(cursor.getColumnIndexOrThrow("art_movement")));
                artwork.setLocation(cursor.getString(cursor.getColumnIndexOrThrow("location")));
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error retrieving artwork: " + e.getMessage());
        }
        return artwork;
    }

    // retrieve artwork by id
    public Artwork getArtwork(int id, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        Artwork artwork = null;

        try (Cursor cursor = db.rawQuery("SELECT * FROM artworks WHERE id = ?", new String[]{String.valueOf(id)})) {

            if (cursor != null && cursor.moveToFirst()) {
                artwork = new Artwork(-1, null, null, null,
                        null, null, null, null, null);

                artwork.setImageResId(cursor.getInt(cursor.getColumnIndexOrThrow("image_res_id")));
                artwork.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                artwork.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                artwork.setArtist(cursor.getString(cursor.getColumnIndexOrThrow("artist")));
                artwork.setYear(cursor.getString(cursor.getColumnIndexOrThrow("year")));
                artwork.setMedium(cursor.getString(cursor.getColumnIndexOrThrow("medium")));
                artwork.setDimensions(cursor.getString(cursor.getColumnIndexOrThrow("dimensions")));
                artwork.setArtMovement(cursor.getString(cursor.getColumnIndexOrThrow("art_movement")));
                artwork.setLocation(cursor.getString(cursor.getColumnIndexOrThrow("location")));
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error retrieving artwork: " + e.getMessage());
        }
        return artwork;
    }

    public void deleteArtwork(Artwork artwork) {
        String title = artwork.getTitle();
        String artist = artwork.getArtist();

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String selection = "title = ? AND artist = ?";
            String[] selectionArgs = {title, artist};

            int deletedRows = db.delete("artworks", selection, selectionArgs);

            if (deletedRows > 0) {
                Log.d("Database", "Artwork deleted successfully");
            } else {
                Log.d("Database", "No artwork found with the given title and artist");
            }
        } catch (Exception e) {
            Log.e("Database", "Error deleting artwork", e);
        }
    }

}
