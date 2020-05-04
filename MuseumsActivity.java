package com.example.agendiario;

/**
 *
 * Creative Commons (CC) 2019 Marcos Vinícius da Silva Santos and Marcos Antonio dos Santos
 *
 * Licensed under the Creative Commons, Version 4.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://creativecommons.org/licenses/by-nc-sa/4.0/
 *    https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode
 *    Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0)
 *
 *    https://creativecommons.org/licenses/by-nc-sa/4.0/deed.pt_BR
 *    https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.pt
 *    Atribuição-NãoComercial-CompartilhaIgual 4.0 Internacional (CC BY-NC-SA 4.0)
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 Considerações Iniciais

 1 - Crie na pasta res/layout o arquivo                   activity_museums.xml

     Utilize o link para o código do arquivo
     https://github.com/1268marcos/youtube_android/blob/master/activity_museums.xml
     Obs.: Alguns outros itens poderão ser necessários no arquivo

 2 - Crie o recurso de menu em res/menu o arquivo         main_menu_museums.xml

     Utilize o link para o código do arquivo
     https://github.com/1268marcos/youtube_android/blob/master/main_menu_museums.xml
     Obs.: Alguns outros itens poderão ser necessários no arquivo

 3 - Revisar e criar os elementos ausentes no arquivo     strings.xml   na pasta res/values

         <string name="save_success">Sucesso ao Gravar</string>
         <string name="save_fail">Falha ao Gravar</string>
         <string name="update_fail">Falha ao Alterar</string>
         <string name="update_success">Sucesso ao Alterar</string>

     Utilize o link para o código do arquivo
     https://github.com/1268marcos/youtube_android/blob/master/strings.xml
     Obs.: Alguns outros itens poderão ser necessários no arquivo

 4 - Crie na pasta res/layout o arquivo                   recyclerview_item_museums.xml

     Utilize o link para o código do arquivo
     https://github.com/1268marcos/youtube_android/blob/master/recyclerview_item_museums.xml
     Obs.: Alguns outros itens poderão ser necessários no arquivo

 5 - Verificar se a classe    MuseumViewModel.java         está no mesmo package (pacote)
     Essa classe irá trabalhar com o observador nos dados (em vez de pesquisar mudanças) e
     atualizar apenas a interface do usuário (View) quando os dados sofrerem algum tipo de mudança.

 6 - Verificar se a classe    MuseumAdapter.java           está no mesmo package (pacote)
     É a responsável pela ligação do RecyclerView com os itens que serão exibibidos na lista
     através do método 'onBindViewHolder()'. Em caso de 'crash' do aplicativo ao abrir a atividade
     'ActitivyMuseums' sugiro verficar a programação do método 'onBindViewHolder()',
     principalmente, se os dados a serem ligados não são do tipo String (é apenas uma dica).

 7 - Verificar se a classe    MuseumAddEditActivity.java   está no mesmo package (pacote)

 8 - Configurar a interface de pesquisa (filtro)

     O uso do widget SearchView como item na barra de apps é a maneira preferencial para oferecer o
     recurso de pesquisa no app. Assim como em todos os itens da barra de apps, você pode definir
     que a SearchView seja sempre exibida, seja exibida apenas quando houver espaço ou como uma
     ação recolhível, que exibe a SearchView como um ícone inicialmente e depois ocupa toda a barra
     de apps como um campo de pesquisa quando o usuário clica no ícone.

     Para adicionar um widget SearchView à barra de apps, crie um arquivo na pasta res/menu com o
     nome de     main_menu_museums.xml     no projeto e adicione o seguinte código no arquivo
     entre a marcação <menu xmlns:android="http://schemas.android.com/apk/res/android">    </menu>

         <item
         android:id="@+id/action_search_museums"
         app:actionViewClass="androidx.appcompat.widget.SearchView"
         android:icon="@drawable/ic_search"
         android:orderInCategory="100"
         android:title="@string/action_search"
         app:showAsAction="ifRoom" />

         <item
         android:id="@+id/delete_all_museums"
         android:title="@string/delete_all_item"
         app:showAsAction="never"
         />

     Utilize o link para o código do arquivo
     https://github.com/1268marcos/youtube_android/blob/master/main_menu_museums.xml
     Obs.: Alguns outros itens poderão ser necessários no arquivo
 
 9 - Configurar o arquivo AndroidManifest.XML para incluir o recurso de filtro para a atividade

     Localize <activity android:name=".MuseumsActivity"></activity>

     Acrescente a intent do recurso como apresentado abaixo
         <activity android:name=".MuseumsActivity">

              <intent-filter>
                  <action android:name="android.intent.action.SEARCH" />
              </intent-filter>

          </activity>

 */

/**
 Classe 'MuseumsActitivy'

 Responsável por exibir a atividade principal referente aos Museu, onde é exibindo em uma lista do
 tipo (recyclerview e seus itens) e gerenciar o que ocorre com a próxima atividade (intenção) que
 permite adicionar um novo (inserir) museu e ou editar (alterar) os dados de um já existente.

 Recebemos o resultado da intenção (inserir ou alterar). Tudo OK ou não com as operacões.

 Ao configurar a exibição da lista de museus no RecyclerView adicionamos o comportamento
 associado ao item da lista (deslizar para esquerda ou direita e remover da lista). Aqui surge uma
 questão relacionada a utilização desta prática em confronto com a experiência do usuário.

 É configurada a barra do aplicativo com o botões voltar e pesquisar (padrão) e o menu onde há uma
 opção para excluir todos os museus da lista.

 Essa classe herda a classe AppCompatActitity que permite usar o ActionBar.

 */

public class MuseumsActivity extends AppCompatActivity {

    /**
     Constante 'ADD_MUSEUM_ACTIVITY_REQUEST_CODE'

     Constante que representa a atividade Inserir (ADD) definida com o código de solcitação 1

     Uma constante é definida com o uso da palavra reservada 'final' que ao ser aplicada a uma
     variável significa que ela não pode ser alterada após a sua inicialização, ou seja, seu
     valor inicial não pode ser alterado. Quando aplicamos 'final' a um método indicamos que esse
     método não pode ser sobreescrito (sobrecarregado). Aplicando 'final' em uma classe indica que
     não podemos substituí-la.
     */
    public static final int ADD_MUSEUM_ACTIVITY_REQUEST_CODE = 1;

    /**
     Constante 'EDIT_MUSEUM_ACTIVITY_REQUEST_CODE'

     Constante que representa a atividade Inserir (ADD) definida com o código de solcitação 2
     */
    public static final int EDIT_MUSEUM_ACTIVITY_REQUEST_CODE = 2;

    /**
     Variável 'TEXT_SEARCH_RESULT'

     Essa variável irá conter o texto que utilizamos em uma pesquisa/filtro
     */
    public String TEXT_SEARCH_RESULT = "com.example.agendiario.TEXT_SEARCH_RESULT";

    /**
     Objeto 'mMuseumViewModel'

     Criado a partir da classe 'MuseumViewModel'

     Contém os métodos para ações de CRUD no banco de dados
     */
    private MuseumViewModel mMuseumViewModel;

    /**
     Objeto 'fabMuseums'

     Criado a partir da classe 'FloatingActionButton'

     Representa o botão de ação flutante para inserir um novo museu
     */
    private FloatingActionButton fabMuseums;

    /**
     Objeto 'adapter'

     Criado a partir da classe 'MuseumAdapter'

     Esse objeto faz o ligação entre o RecyclerView e o ViewHolder. O adapter permitem personalizar
     a forma como um item de uma lista (um componente) é apresentado para o usuário.

     Lembrando que o ViewHolder é uma abordagem utilizada para guardar um conjunto de views para
     que possam ser eficientemente acessadas e reutilizadas, quando necessário, ou seja, ela guarda
     cada item a ser exibido na recyclerview.

     Importante - As listas formam uma categoria de componentes no Android. A abordagem recomendada
     é fazer uso das RecyclerViews, porém, há a as ListViews.

     */
    private MuseumAdapter adapter;

    /**
     Método 'setupRecyclerView()'

     Temos a configuração da recyclerview.
     */

    private void setupRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_museums);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new MuseumAdapter(this);

        recyclerView.setAdapter(adapter);

        mMuseumViewModel = ViewModelProviders.of(this).get(MuseumViewModel.class);

        //Importar
        // import androidx.lifecycle.Observer;
        // cuidado para não importar essa vai dar erro  --> import java.util.Observer;
        // atenção ao autocompletar do código

        mMuseumViewModel.getFullMuseums().observe(this, new Observer<List<Museum>>(){
            @Override
            public void onChanged(@Nullable final List<Museum> museums){
                adapter.setMuseums(museums);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mMuseumViewModel.delete(adapter.getMuseumAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MuseumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Museum museum) {
                Intent intent = new Intent(MuseumsActivity.this, MuseumAddEditActivity.class);
                intent.putExtra(MuseumAddEditActivity.EXTRA_ID, museum.getId());
                intent.putExtra(MuseumAddEditActivity.EXTRA_NAME, museum.getName());
                intent.putExtra(MuseumAddEditActivity.EXTRA_STYLE, museum.getStyle());
                intent.putExtra(MuseumAddEditActivity.EXTRA_SCORE, museum.getScore());
                startActivityForResult(intent, EDIT_MUSEUM_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MUSEUM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Museum museum = new Museum(data.getStringExtra(MuseumAddEditActivity.EXTRA_NAME).trim(),
                    data.getStringExtra(MuseumAddEditActivity.EXTRA_STYLE).trim(),
                    data.getIntExtra(MuseumAddEditActivity.EXTRA_SCORE,0),
                    data.getStringExtra(MuseumAddEditActivity.EXTRA_CREATE));
            mMuseumViewModel.insert(museum);
            Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_MUSEUM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(MuseumAddEditActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, R.string.update_fail, Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(MuseumAddEditActivity.EXTRA_NAME).trim();
            String style = data.getStringExtra(MuseumAddEditActivity.EXTRA_STYLE).trim();
            int score = data.getIntExtra(MuseumAddEditActivity.EXTRA_SCORE, 0);
            String create = data.getStringExtra(MuseumAddEditActivity.EXTRA_CREATE);
            Museum museum = new Museum(name, style, score, create);
            museum.setId(id);
            mMuseumViewModel.update(museum);
            Toast.makeText(this, R.string.update_success, Toast.LENGTH_SHORT).show();

        } else {
            //aqui temos um bug
            //Toast.makeText(this, R.string.save_fail, Toast.LENGTH_SHORT).show();
            // https://stackoverflow.com/questions/33396179/app-crashed-when-the-return-button-is-clicked
            //https://developer.android.com/guide/navigation/navigation-custom-back
            if(resultCode != RESULT_CANCELED) {
                Toast.makeText(this, R.string.save_fail, Toast.LENGTH_SHORT).show();
            }

        }

    }


    //final em protected void onCreate ?  explicar isso
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_museums);

        // 02.05.2020 sugestao Daniel Henrique  https://stackoverflow.com/questions/15686555/display-back-button-on-action-bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupRecyclerView();

        fabMuseums = findViewById(R.id.fab_museums);
        fabMuseums.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MuseumsActivity.this, MuseumAddEditActivity.class);
                startActivityForResult(intent, ADD_MUSEUM_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    // continuação 29.04.2020
    // sugestao Daniel Henrique
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



    // importar a classe Menu
//    //https://www.youtube.com/watch?v=QJUCD32dzHE 5:30
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // até return true; irá indicar erro, pois, o método exige um retorno
        // criar o menu na AppBar
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu_museums, menu);

        // Obtenha o SearchView e defina a configuração pesquisável
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        // atencao para importar   import androidx.appcompat.widget.SearchView;
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search_museums).getActionView();
        // Assume que a atividade atual é a atividade pesquisável
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // esteja atento após o new para usar o autocompletar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // variável TEXT_SEARCH_RESULT recebe o que será pequisável
                TEXT_SEARCH_RESULT = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter( newText);
                return false;
            }
        });

        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // até return true; indica que há erro para o método
        switch (item.getItemId()){
            case R.id.delete_all_museums:
                mMuseumViewModel.deleteAll();
                Toast.makeText(this, R.string.message_delete_all, Toast.LENGTH_SHORT ).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
