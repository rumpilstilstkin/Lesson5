package com.geekbrains.sqlietedatabaseexample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Note> elements;
    ArrayAdapter<Note> adapter;
    ListView listView;
    NoteDataSource notesDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesDataSource = new NoteDataSource(getApplicationContext());
        notesDataSource.open();

        //Создаем массив элементов для списка
        elements = notesDataSource.getAllNotes();

        // Связываемся с ListView
        listView = (ListView) findViewById(R.id.list);

        // создаем адаптер
        adapter = new ArrayAdapter<Note>
                (this, android.R.layout.simple_list_item_1, elements);

        // устанавливаем адаптер списку
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(elements.get(position).getTitle());
                builder.setMessage(elements.get(position).getNote());
                builder.show();
            }
        });

        // регестрируем контекстное меню на список.
        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // обработка нажатий
        switch (item.getItemId()) {
            case R.id.menu_add:
                addElement();
                return true;
            case R.id.menu_clear:
                clearList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editElement(info.position);
                return true;
            case R.id.menu_delete:
                deleteElement(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void clearList() {
        notesDataSource.deleteAll();
        dataUpdated();
    }

    private void addElement() {
        // без этого блока в OnClickListener() не получить доступ к нашему EditText
        ///
        LayoutInflater factory = LayoutInflater.from(this);
        // final - для того чтобы использовать его в OnClickListener()
        final View alertView = factory.inflate(R.layout.layout_add_note, null);
        ///

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alertView);
        builder.setTitle(R.string.alert_title_add);
        builder.setNegativeButton(R.string.alert_cancel, null);
        builder.setPositiveButton(R.string.menu_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText editTextNote = (EditText) alertView.findViewById(R.id.editTextNote);
                EditText editTextNoteTitle = (EditText) alertView.findViewById(R.id.editTextNoteTitle);
                // если использовать findViewById без alertView то всегда будем получать editText = null
                notesDataSource.addNote(editTextNoteTitle.getText().toString() ,editTextNote.getText().toString());
                dataUpdated();
            }
        });
        builder.show();
    }

    private void editElement(int id) {
        notesDataSource.editNote(elements.get(id).getId(), "Edited", "Edited title");
        dataUpdated();
    }

    private void deleteElement(int id) {
        notesDataSource.deleteNote(elements.get(id));
        dataUpdated();
    }

    private void dataUpdated() {
        elements.clear();
        elements.addAll(notesDataSource.getAllNotes());
        adapter.notifyDataSetChanged();
    }
}

