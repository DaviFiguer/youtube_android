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

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


/**
 Considerações Iniciais

 [1] É altamente recomendável usar a biblioteca de persistência de dados Room em vez
     da biblioteca SQLite diretamente

 [2] As camadas da Room são representadas por três componentes: Entity, Dao e Database

 [3] Entity : são as entidades responsáveis por mapear as tabelas. Representam
     uma tabela dentro do banco de dados. Por exemplo, a classe 'Museum' representa
     a tabela 'museums' assim definida      @Entity(tableName = "museums")

 [4] Dao (Data Access Object) : são as interfaces utilizadas para acessar os dados
     armazenados no banco. Contém os métodos (insert, update, delete, query) utilizados
     para acessar o banco de dados. Por exemplo, a Interface 'MuseumDao' foi assim
     definida      @Dao public interface MuseumDao {... métodos ...}

 [5] Database : é a representação da classe abstrata do Banco de Dados. Esta classe
     receberá uma anotação '@Database' que irá identificar as Entities, os Dao’s e
     seus Converters. Ele será responsável por fazer o controle do banco de dados.

 [6] Cada instância RoomDatabase é bastante cara e raramente é necessário ter acesso
     a várias instâncias em um único processo.

 [7] Essa classe será utilizada por outra classe definida como repositório para
     os dados com operações de CRUD e sincronização de dados na nuvem (se houver).
 */


/**
 Anotação '@Database'

 Marca uma classe como um RoomDatabase. A classe deve ser uma classe abstrata e
 estendida RoomDatabase.

 Como exemplo:

 @Database(entities = {Book.class, City.class}, version = 1)

 O exemplo anterior define uma classe que possui 2 tabelas (Entidades) e estamos utilizando
 a versão 1 do banco de dados. Não há limites para o número de Entidades ou classes Dao,
 mas elas devem ser exclusivas no banco de dados.

 */

@Database(entities = {Book.class, City.class, MyPersonal.class, Museum.class}, version = 4)


/**
 Classe Abstrata 'DbRoomDatabase'

 Classes abstratas são classes que, basicamente, são apenas uma inspiração abstrata
 de como as classes que a herdarem devem se comportar. Ela é um meio termo entre a classe
 concreta e a interface.

 Classes abstratas não podem ser instanciadas. Elas servem apenas para que outras classes
 usem-na como modelo (herdem os atributos/propriedades e métodos delas).

 Elas podem ter métodos abstratos ou não abstratos. Os métodos abstratos não podem ter
 corpo, ou seja, deve-se declarar apenas a assinatura do método e eles obrigatoriamente
 terão que ser implementados na classe filha (a classe que herda), já os métodos que não
 forem assinados como abstract devem ter corpo e podem ou não ser sobrescritos na
 classe filha.

 Uma classe abstrata pode conter lógica (código).

 */

public abstract class DbRoomDatabase extends RoomDatabase {

    /**
     Interfaces 'Dao' e membros utilizados pela classe 'DbRoomDatabase'

     Declaramos TODOS os membros 'Dao' que serão utilizados para acessar os dados
     armazenados no banco de acordo com a respectiva entidade (tabelas)
     */
    public abstract BookDao mBookDao();
    public abstract CityDao mCityDao();
    public abstract MyPersonalDao mMyPersonalDao();
    public abstract MuseumDao mMuseumDao();

    /**
     Singleton 'INSTANCE'

     Singleton é um padrão de projeto (Design Pattern) de software. Este padrão garante a
     existência de apenas uma instância de uma classe, mantendo um ponto global de acesso
     ao seu objeto.

     O padrão Singleton garante que uma classe tenha apenas uma instância na aplicação
     inteira, gerenciando-a de dentro da classe a fim de evitar que outra classe crie
     outra instância. Lembre-se, o padrão RoomDatabase é caro para a aplicação. Porém,
     afirmar que o uso de Singleton reduz o consumo de memória pode não ser verdadeiro,
     devemos levar em consideração que a redução do consumo de memória ocorre no
     contexto de não termos instâncias desnecessárias de uma classe que é utilizada
     com frequência por toda a aplicação.

     A vantagem é que o padrão Singleton pode ser instanciado e usado apenas quando
     necessário, diferentemente se criássemos uma variável com escopo global em que o objeto
     é sempre criado quando o aplicativo é inicializado e poderá estar usando recursos
     que não são necessários neste momento. O padrão Singleton define um ponto único
     de acesso global sendo inclusive muito mais fácil de gerenciar a criação e
     utilização da instância.

     Definimos para a classe 'DbRoomDatabase' um singleton representado por
     'INSTANCE' e dessa forma temos apenas um ponto de acesso central a esta instância
     da classe.

     Um outro exemplo de uso para Singleton seria para uma infraestrutura de log de
     dados na aplicação.

     O uso de Singleton deve ser feito com moderação, sabedoria e planejamento, senão,
     ele acaba se transformando um anti-pattern.

     Para saber sobre vantagens do uso de Singletons faça uma leitura em
     https://www.opus-software.com.br/singleton-design-pattern/

     */

    private static DbRoomDatabase INSTANCE;

    /**
     Método 'getDatabase()'

     Sua funcionalidade é verificar se há uma instância do banco de dados em memória
     no contexto atual do aplicativo aberto e retornar ('return INSTANCE')essa instância
     para ser utilizada em outro ponto do aplicativo.

     Se a instância é nula, ou seja, não existe obtemos a instância para o banco de dados
     aqui representado por 'db_agendiario'

     Mas o que é uma instância?

     Rapidamente falando : a instância seria a 'materialização' da classe, o que gera
     um objeto na memória. Algo com o qual você pode interagir.

     A instância é, digamos, a coisa técnica que permite a existência de um objeto em
     memória. Diz James Martin em seu livro 'Análise e Projeto Orientados a
     Objeto' : "Um objeto é uma instância de um tipo de objeto". A palavra 'Objeto', por
     sua vez, pode ser usada num nível mais alto de abstração e foi o que James tentou dizer.

     Observe que a palavra 'Objeto' existe porque o conceito da Orientação a Objetos é
     representar no software as características e comportamentos relevantes dos objetos reais
     do domínio (aqui você pode pensar em um aluno, um lanche, uma fruta, etc.).

     Então ao modelar, por exemplo, um sistema de notas fiscais, a própria nota e seus itens
     são objetos; e ao codificar o sistema, as classes serão declaradas para definir estes
     objetos, e para que estes objetos existam na memória serão criadas instâncias
     destas classes.

     Ao ver o seguinte código:           O que ele representa?

       class Museum{ }                   Não é objeto e nem instância, é a
                                         definição de um objeto com seus atributos e
                                         métodos, ou seja, a classe 'Museum'

       museum = new Museum()            'museum' referencia de uma instância da
                                         classe Museum(), ou seja, o objeto 'museum'

       museum.setName("MASP")            a instância representa o Museu chamado MASP,
                                         que é um objeto de domínio (fisicamente na
                                         Avenida Paulista, ou seja, está lá no mundo real) e
                                         existe como objeto para além do código fonte

     Depois de os objetos serem persistidos, ou seja, os objetos, seus estados e os dados
     associados a ele foram salvos e que poderá ser recuperado quando necessário no futuro,
     as instâncias são removidas da memória e os objetos como os conhecemos no mundo real
     estarão seguros na base de dados até que precisemos lidar com eles novamente - e daí
     novamente vamos criar instâncias das suas classes para poder olhar (ou usar) para
     estes objetos e manipulá-los.

     E se os objetos forem persistidos num banco de dados orientado a objetos, os objetos
     continuam a existir no âmbito técnico além do conceitual mesmo depois de suas instâncias
     não existirem mais na memória.

     Para conhecer mais sobre persistência de dados faça uma leitura em
     https://take.net/blog/devs/persistencia-de-dados
     https://bit.ly/2XuufVc

     */

    static DbRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DbRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DbRoomDatabase.class, "db_agendiario")

                            .fallbackToDestructiveMigration()
                            // Destrói e recria as tabelas do banco ao invés de fazer a
                            // migração por não haver nenhum objeto para a migração de dados.

                            .addCallback(sRoomDatabaseCallback)
                            // Adicionar o retorno de chamada

                            .build();
                            // Construir a instância
                }
            }
        }
        return INSTANCE;
        // Sobre migração leia mais em https://bit.ly/3b34FdZ
    }


    /**
     Classe 'RoomDatabase.Callback' e objeto 'sRoomDatabaseCallback'

     Representa que o banco de dados foi aberto. Em nosso código há uma chamada comentada
     para que se inclua um conjunto de dados pré-definidos programaticamente aqui
     sendo repsentado pela classe 'PopulateDbAsync' e seus métodos.

     */

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // Se você deseja manter os dados através da reinicialização do aplicativo,
            // comente a linha de código     new PopulateDbAsync(INSTANCE).execute();
            // Ao não comentar será executada a classe 'PopulateDbAsync' que foi criado para
            // inserir um conjunto de dados iniciais no aplicativo
            //
            // new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
      Classe 'PopulateDbAsync'

      Popula (preencher) as tabelas do banco de dados em segundo plano.
      Se você deseja começar com mais livros, cidade e etc., basta adicioná-las ao código

      Aviso - Esta classe é apresentada aqui para fins didáticos e não será utilizada.

     */

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private final BookDao mBookDao;
        private final CityDao mCityDao;
        private final MyPersonalDao mMyPersonalDao;

        PopulateDbAsync(DbRoomDatabase mDbRoomDatabase){
            mBookDao= mDbRoomDatabase.mBookDao();
            mCityDao = mDbRoomDatabase.mCityDao();
            mMyPersonalDao = mDbRoomDatabase.mMyPersonalDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            mBookDao.deleteAllBooks();
            Book book = new Book("Meu pe de laranja lima", "Jose Mauro Vasconcelos", 10, "28/01/2020 17:03:55.000");
            mBookDao.insert(book);

            mCityDao.deleteAllCities();
            City city = new City("Carapicuiba","Sao Paulo", 3.5f);
            mCityDao.addCity(city);

            mMyPersonalDao.deleteAllMyPersonals();
            MyPersonal myPersonal = new MyPersonal("Marcos Santos", "1268marcos", -33093000, "", "Universidade Federal Fluminense" , "Pós Graduação", 2013, 1581380294   );
            mMyPersonalDao.insert(myPersonal);

            return null;
        }
    }

}
