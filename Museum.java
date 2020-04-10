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
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 Considerações Iniciais

 [1] Nosso aplicativo irá fazer da biblioteca SQlite que implementa um mecanismo de
     banco de dados SQL (pequeno, rápido, independente, de alta confiabilidade,
     multiplataforma, compatível com versões anteriores, etc.). O SQlite é o mecanismo
     de banco de dados mais usado no mundo. Esse mecanismo de banco de dados será
     disponibilizado junto do aplicativo.

     Saiba mais sobre o SQlite no link
     https://www.sqlitetutorial.net/what-is-sqlite/

 [2] Faremos uso da biblioteca de persistência de dados Room que oferece uma camada de
     abstração sobre o SQlite para permitir acesso fluente e robusto ao banco de dados.

     Room é uma das bibliotecas existentes dentro do conjunto “Android JetPack” apresentado
     durante o Google I/O de 2018, ela auxilia os desenvolvedores criando uma abstração
     das camadas de banco de dados (SQLite).

     Aplicativos que processam quantidades não triviais de dados estruturados podem se
     beneficiar muito da persistência desses dados localmente. O caso de uso mais comum é
     armazenar as partes de dados relevantes em cache (na memória do dispositivo). Dessa
     forma, quando o dispositivo não conseguir acessar a rede, o usuário ainda poderá
     navegar pelo conteúdo enquanto estiver off-line. Todas as alterações de conteúdo
     feitas pelo usuário serão sincronizadas com o servidor quando o dispositivo ficar
     on-line novamente. Portanto, é altamente recomendável usar o Room em vez do SQLite,
     porque o Room cuida desses problemas para você.

     Saiba mais sobre como aplicar os recursos da Room no link
     https://developer.android.com/training/data-storage/room

     É necessário importar a dependência Room para o projeto do aplicativo em
     Build.Gradle(app). O link apresenta como fazer isso para o seu aplicativo
     https://developer.android.com/jetpack/androidx/releases/room

 [3] Utilizaremos para desenvolvimento do aplicativo um modelo didático baseado no
     padrão arquitetural Model View ViewModel (MVVM). O objetivo do MVVM é prover
     uma separação de responsabilidades, entre a view e sua lógica.

     Separar responsabilidades? Pra quê? A criação de testes para o Android não é algo
     assim tão simples. A falta de organização e a mistura entre as responsabilidades
     (aplicativo e Android) pode gerar problemas de acoplamento entre o seu código
     e o código do Sistema Operacional (SO), ou seja, seu teste pode ficar impossibilitado
     de executar na JVM (Máquina Virtual Java - programa que carrega e executa os
     aplicativos Java, convertendo os bytecodes em código executável de máquina. A JVM é
     responsável pelo gerenciamento dos aplicativos, à medida que são executados), pois,
     pode existir alguma dependência que necessite de classes do SO para ser executada.

     O padrão (pattern) MVVM foi criado por John Gossman (em 2005). Gossman foi um
     dos arquitetos do WPF (Windows Presentation Foundation) e Silverlight na Microsoft.

     O MVVM assemelha-se em alguns aspectos ao padrão MVC (Model View Controller) e
     ao padrão MVP (Model View Presenter). O MVVM busca estabelecer uma clara
     separação de responsabilidades entre o (M)odelo de objetos (classes de negócio,
     serviços externos e até mesmo de acesso a banco de dados) e a (V)iew que é a
     interface, com a qual o utilizador interage.

     A camada Model(Modelo) não conhece a View (Camada de apresentação) e vice-versa. Na
     verdade a View conhece a camada ViewModel e se comunica com ela através de um
     mecanismo conhecido por binding. A View através do data binding interage com a ViewModel
     notificando a ocorrência de eventos e o disparo de comandos. A ViewModel por
     sua vez, responde a esta notificação realizando alguma ação no modelo seja obtendo
     algum dado, atualizando ou inserindo informações no modelo.

     Model – Implementação do modelo de domínio da aplicação que inclui o modelo de
     dados, regras de negócio e validações de lógica. Sendo um pouco mais detalhista,
     o Model no MVVM, encapsula a lógica de negócios e os dados. O modelo nada
     mais é do que o chamado modelo de domínio de uma aplicação, ou seja, as classes de
     negócio que serão utilizadas em uma determinada aplicação. O modelo também contém os
     papéis e também a validação dos dados de acordo com o negócio, cuja aplicação
     em questão busca atender.

     View – A responsabilidade da View é definir a aparência ou estrutura que o
     usuário vê na tela. Deixando um pouco mais claro: ela é a entidade responsável
     por definir a estrutura, layout e aparência do que será exibido na tela. Dentro
     do nosso contexto, as Views são nossas Activities, Fragments e elementos visuais
     criados para serem disponibilizados na tela.

     ViewModel – A responsabilidade da ViewModel no contexto do MVVM, é disponibilizar
     para a View uma lógica de apresentação. Detalhando: a ViewModel age como
     um intermediário entre a View e o Model, é o responsável por manusear o Model
     para ser utilizado pela View. Ele utiliza o databinding para notificar mudanças
     aos observadores, no caso a View.

     Data binding - É uma técnica que liga fontes de dados entre um provedor de
     conteúdo (banco de dados ou serviço em nuvem) e seu consumidor (aplicativo)
     mantendo os valores sincronizados.
 */

/**
 @Entity
 Por fazermos uso da Room teremos que possuir um conjunto de dados relacionados
 que são definidos como entidades, ou seja, para cada entidade, uma tabela é criada
 no banco de dados (objeto Database) asssociado para armazenar os itens.

 O nome da tabela 'museums' deverá ser informada no objeto Database. Se após a primeira
 execução com sucesso do aplicativo haver a necessidade de alguma mudança na estrutura
 de dados que foi definida AQUI nesta classe 'Museum' uma nova versão do banco de dados
 deverá ser informada na classe 'DbRoomDatabase', afinal de contas é necessário avisar
 na classe 'DbRoomDatabase' que uma mudança ocorreu aqui. A classe 'DbRoomDatabase' não
 foi codificada até este momento.

 Detalhe importante [1] - os dados contidos na tabela 'museums' e em outras tabelas
 do banco de dados serão perdidos a não ser que você faça a migração de dados. Para
 saber mais e aprender como fazer a sua migração de dados leia
 https://developer.android.com/training/data-storage/room/migrating-db-versions ,
 https://proandroiddev.com/migrating-to-room-in-the-real-world-part-1-a16358e9027 e
 https://bit.ly/3aZDnVF .

 Detalhe importante [2] - se não indicar um novo número de versão o aplicativo irá
 falhar (crash), ou seja, parar de funcionar. Leia mais sobre crash em
 https://developer.android.com/topic/performance/vitals/crash

 Detalhe importante [3] - tenha cuidado ao nomear suas tabelas, pois, os nomes das
 tabelas no SQLite são indiferentes a maiúsculas.

 A estrutura de dados definida AQUI para esta classe/entidade faz parte do seu estudo
 sobre modelagem de dados. Essa modelagem é originária de muitos estudos e análises
 realizada junto ao seu seu cliente com o levantamento de todas as necessidades, dados e
 requisitos necessários, inclusive com pesquisas externas, para o desenvolvimento do
 aplicativo buscando atender a máxima defendida pelo seu professor que é
 "Nós temos a solução para o seu problema".

 O cliente deste aplicativo é o Felipe que tem uma agenda em papel com vários espaços
 para anotações onde ele registra os livros, museus, filmes e etc. Felipe faz avalições
 destes e outros temas. Há um espaço para avaliação diária de questões ligadas ao seu
 dia-a-dia. Relatórios com estatísticas e alertas serão disponibilizados para o
 aplicativo. Desta breve descrição veio o nome do aplicativo - 'AGENDIARIO' - ou seria
 melhor, 'AGENDARIO' (agenda+diário).

 https://developer.android.com/training/data-storage/room/defining-data
 */
@Entity(tableName = "museums")


/**
 classe 'Museum'

 Representa a estrutura de dados da entidade/tabela MUSEU. As colunas (campos da tabela)
 ID, NAME, STYLE, SCORE e CREATIONDATE serão criadas como membros privados da classe. As
 restricões de cada coluna serão informadas com o uso da anotação '@' de acordo com o
 tipo correspondente.
 */
public class Museum {

    /**
     Colunas, restrições e construtor da classe

     Para manter um campo, o Room precisa ter acesso a ele. Você fará isso criando
     membros privativos da classe e em seguida criará os "getter" e "setter" públicos
     para esse campo.

     Destaco que o Room utiliza as convenções do JavaBeans, ou seja,
     um 'acordo' para especificar estruturas de dados disfarçadas de classe com um
     construtor vazio para a classe. Porém para nossos estudos nosso construtor não será
     vazio.

     Para construir os membros da classe:

     [1] utilizar o menu FILE > SETTINGS
     [2] expandir o item EDITOR > CODE STYLE > JAVA
     [3] clique na guia CODE GENERATION
     [4] na caixa FIELD        digite   m
     [5] na caixa STATIC FIELD digite   s
     Com isso indicamos ao Android Studio como proceder para criar os gets e sets a
     partir dos (m)embros da classe.

     Escreva a lista de membros utilizando o modificador de acesso privado:

     private int mId;
     private String mName;
     private String mStyle;
     private int mScore;
     private String mCreationDate;

        Ao definir os membros como privativos da classe estamos definindo a capacidade
        da classe em ocultar os detalhes de sua implementação (construção do código)
        para outras classes e programas externos. Isso é importante, pois, a comunicação
        entre a classe e outros programas somente se dará através do uso de uma API.

        API (Application Programming Interface) é uma forma de integrar sistemas,
        possibilitando benefícios como a segurança dos dados, facilidade no intercâmbio
        entre informações com diferentes linguagens de programação e a cobrança por
        acessos (monetização). Já que falei de monetização, pense no conjunto de dados
        que o aplicativo 'AGENDARIO' poderá coletar e armazenar em nuvem.

     Clique com o botão direito do mouse sobre qualquer um dos membros e escolha
     a opção GENERATE... e depois GETTER AND SETTER

     Na janela que será apresentada certifique-se que os membros tem seu nome
     iniciados por        m       . Selecione todos os membros com o uso de CTRL+clique
     e depois confirme no botão OK a criação dos métodos acessores get e set. Os métodos
     acessores servem para pegarmos (get) ou definirmos (set) informações dos
     membros (variáveis) da classe que são definidas como 'private'

     Encontre os membros e faça as anotações '@' das restrições necessárias.

     Caso seja necessário, por exemplo, converter o conteúdo textual em maiúsculo e
     remover espaços em branco do membro você pode fazer isso no 'set' do membro. Acredite,
     fazer isso aqui nesta classe vai te poupar alguns detalhes de programção e
     manutenção do código mais a frente em função destas regras estarem definidas aqui.
     Veja o exemplo:
     public void setName(String name) {
        mName = name.toUpperCase().trim();
     }

     Clique com o botão direito do mouse sobre o nome da classe 'Museum' e escolha
     a opção GENERATE... e depois CONSTRUCTOR

     Na janela que será apresentada selecione os membros que farão parte do construtor
     da classe com o uso de CTRL+clique e depois confirme no botão OK. O construtor é
     definido como sendo um método cujo nome deve ser o mesmo nome da classe e sem
     indicação do tipo de retorno (nem mesmo void). O construtor é unicamente
     chamado (invocado) no momento da criação do objeto que representa a classe através
     do operador new (instância da classe com   = new). O retorno do operador new é
     uma referência para o objeto recém-criado.
     */

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "museumId")
    private int mId;

    @Nullable
    @ColumnInfo(name = "museumName")
    private String mName;

    private String mStyle;

    public Museum(@Nullable String name, String style, int score, String creationDate) {
        mName = name;
        mStyle = style;
        mScore = score;
        mCreationDate = creationDate;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getStyle() {
        return mStyle;
    }

    public void setStyle(String style) {
        mStyle = style;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(String creationDate) {
        mCreationDate = creationDate;
    }

    private int mScore;
    private String mCreationDate;

}
