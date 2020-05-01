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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
  Considerações Iniciais

  [1] Ao utilizar a implementação para filtro (requisito/recurso de busca previsto para
      Museus) na classe 'MuseumAdapter' teremos erro na construção do código até que
      esteja presente o código do objeto 'museumFilter' e do método sobreescrito 'getFilter'

  [2] A classe 'MuseumAdapter' para alimentar (transferir) todos os seus dados para uma
      lista de dados (Dataset), necessita estender a classe 'RecyclerView.Adapter'
      utilizando uma lista que faz parte da classe 'MuseumViewHolder' que até ser
      codificada irá gerar erro. Essa classe cria visualizações para itens e substitui os
      conteúdos das visualizações por novos itens de dados quando um item original não
      está mais visível (rolagem da tela ou eliminação deste item na tela).
      Assim funciona um adaptador de lista.

  [3] A classe 'MuseumAdapter' possui um método sobreescrito 'getItemCount' responsável
      por retornar o número total de itens no conjunto de dados 'mMuseums'
      mantido pelo adaptador. Até que esteja presente no código haverá uma indicação
      de erro na construção da classe 'MuseumAdapter'

  [4] RecyclerView - Componente visual que ficará na Activity/Fragment e irá posicionar
      a lista na tela do usuário, assim como um campo de texto ou botão, por exemplo.

  [5] LayoutManager - É o gerenciador de conteúdo descrito acima. Nele é definido
      qual é a disposição do itens da lista (se será uma lista vertical ou
      horizontal, por exemplo).

  [6] Adapter - Classe responsável por associar a lista de conteúdo/objeto à view
      correspondente. Onde cada objeto da lista será um item na lista. É no Adapter
      onde se define se um item será exibido ou não.

  [7] ViewHolder - É a referência para a view que é a parte visual de cada
      item da lista, que será replicada para todos elementos (na estrutura acima,
      ficaria dentro do Adapter).

  [8] Listener - normalmente implementado em forma de interface. Ele serve para
      escutar o que acontece em um objeto e avisar a outro. É amplamente centrado
      nas Views. Leia mais em https://guides.codepath.com/android/Basic-Event-Listeners
 */

/**
 Classe 'MuseumAdapter'

 Esta classe representa o Adaptador que faz o link entre o RecyclerView e o ViewHolder.
 */

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.MuseumViewHolder> implements Filterable {

    /**
     Classe 'Filter' e objeto 'museumFilter'

     Ao escrever a linha de código: private Filter museumFilter = new Filter(){ } ;

     Esteja atento para finalizar com o    ;     e só depois codificar dentro das {  }

     E por qual motivo? Fazemos referência para a criação de um objeto
     chamado 'museumFilter' que foi instânciado a partir da classe 'Filter'

     Para o objeto 'museumFilter' funcionar deveremos reescrever o comportamento
     de 2 métodos: 'performFiltering' (filtrar os dados de acordo com uma restrição) e
     'publishResults' (publicar os resultados da filtragem na interface do usuário)

     Portanto, até concluir a construção do código do objeto 'museumFilter'
     haverá a sinalização de erro.

     Leia mais em https://developer.android.com/reference/android/widget/Filter
     */

    private Filter museumFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            List<Museum> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() ==0 ) {
                filteredList.addAll(mMuseumsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Museum item : mMuseumsListFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            mMuseums.clear();
            mMuseums.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    /**
     Método 'getFilter()'

     Responsável por retornar o objeto 'musemFilter' que contém os dados
     filtrados, ou seja, a lista de museus que atendem ao critério de busca
     utilizando o nome ou parte do nome de um museu
    */
    @Override
    public Filter getFilter(){
        return museumFilter;
    }

    /**
     Classe 'LayoutInflater' e objeto 'mInflater' (membro da classe MuseumAdapter)

     LayoutInflater é utilizado para uma exibição personalizada. Pega um arquivo XML
     como entrada e cria os objetos View a partir dele. Usaremos para o recurso
     de layout correspondente a um recyclerview onde foram colocados os
     widgets (textview, image, etc.) do Android
    */
    private final LayoutInflater mInflater;

    /**
     Classe 'Context', objeto 'context' e o construtor 'MuseumAdapter'

      Context - É um ponto de acesso para informações globais sobre um
      ambiente de aplicativo. Trata-se de uma classe abstrata cuja implementação
      é fornecida pelo sistema Android. Ela permite acesso a recursos e classes
      específicas de aplicativo, bem como chamadas para operações em nível de
      aplicativo como iniciar activities, enviar ou receber intents por broadcast, etc.

      Em outras palavras, Context é a maneira fornecida pelo sistema para seu
      aplicativo acessar determinados recursos (como o recurso de iniciar uma
      activity, iniciar ou parar um service, enviar um broadcast, abrir um banco
      de dados ou arquivo de preferências, etc.) ou classes (como os diversos
      Managers que o sistema oferece: Gerenciador de Telefonia, de Alarme,
      de Sensores, de Áudio, de Notificações, de Energia, de USB, etc). Esses recursos
      e classes têm a particularidade de serem globais no nível do aplicativo, isto é,
      são de nível de aplicativo. Sem uma instância de Context você não faz muita
      coisa em um aplicativo Android. Context é a classe base para Activity,
      Service, Application, portanto, é uma forma de acessar e lidar com a sua
      aplicação via código. E mais, Context é considerado um 'god object' na programação
      orientada a objetos (POO), ou seja, um objeto que 'sabe demais' ou 'faz muito'
      ele é um 'antipadrão' (prática ruim de programação) da POO. Com aproximadamente
      5500 linhas de código o Context.java possui muitos dados e requer tantos
      métodos, seu papel no programa se torna semelhante a Deus (onisciente e abrangente).
      Em vez de os objetos do programa se comunicarem diretamente entre si, os
      outros objetos do programa confiam no único objeto de Deus para a maioria de suas
      informações e interação.

      Aproveitando o momento...

      Application - Classe-base para aqueles que precisam preservar estado
      global de aplicativo. Você pode fornecer sua própria implementação (normalmente
      não há necessidade, e singletons estáticos podem fornecer a mesma funcionalidade
      de uma maneira mais modular).

      Activity - Uma activity é uma coisa única e focada que o usuário pode fazer.

      Service - É um componente de aplicativo que pode tanto representar o desejo
      de um aplicativo de executar um operação de maior duração ao mesmo tempo em
      que evita interagir com o usuário ou fornecer funcionalidades para outros
      aplicativos usarem.

      Application, Activity e Service são concretizações de Context, isto é, classes
      concretas que implementam um Context do Android. Quando você estende essas
      classes em seu código, você tem acesso aos serviços de nível de aplicativo
      fornecidos pelo Context herdado por essas classes. Além disso, tem acesso a
      recursos específicos de cada subclasse; por exemplo, a Activity ativa no momento
      pode ser fechada através do método finish() e pode executar código no
      thread (tarefa) principal de maneira simples através do método runOnUiThread().
      Activities, Services e Applications têm cada um o seu ciclo de vida particular.
      E nem sempre os contextos são intercambiáveis; por exemplo, se você tentar
      exibir um Dialog passando para ele um contexto Application, isto causará um erro
      devido ao Android esperar que seja passada uma Activity (este problema em
      especial se trata mais de uma característica comportamental ou estrutural
      peculiar do Android, que deveria esperar receber logo uma Activity).

      Observação - o padrão de programação oposto à POO é o pejorativo padrão Ravioli (ou
      Código Espaguete) onde o código fonte é não estruturado e de difícil manutenção
      e entendimento. O código espaguete pode ser causado por vários fatores, como
      requisitos voláteis do projeto, falta de regras de estilo de programação,
      exigências do cliente, capacidade ou experiência insuficientes do programador,
      prazos curtos de entrega do produto e a falta de documentação (principalmente).
     */
    public MuseumAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    /**
     Classe 'List', classe 'Museum' e objeto 'mMuseums' (membro da classe MuseumAdapter)

     List - representa uma coleção ordenada de elementos em uma lista. Podemos
     ter acesso a cada elemento da lista sabendo o seu índice, ou seja, a sua posição
     na lista. Lembrando que a posição do primeiro elemento da lista tem valor 0 (zero)

     Museum - representa a classe da nossa entidade com seus atributos e métodos. É fato
     que NÓS a descrevemos. Temos nela, os métodos 'gets' e 'sets' dos atributos
     (qualidades, características) e o construtor da classe que necessita saber
     quais atributos iremos utilizar

     mMuseums - é um objeto criado a partir da classe Museum que fará parte
     da classe 'MuseumAdapter', portanto, assim como os 'gets'e 'sets' presentes
     na classe 'Museum' todas as características da classe List também farão parte
     deste objeto. Esse objeto representa a lista de museus que esta sendo
     apresentada na View do nosso aplicativo

     mMuseumsListFull - é o mesmo que o objeto 'mMuseums', porém, ele contém a
     lista completa de museus. Seu uso é importante quando realizamos uma busca
     por nome de museu e ao 'deixar' de buscar, reapresentamos na View a lista
     completa evitando consumir recursos de acesso ao banco de dados do
     aplicativo
     */
    private List<Museum> mMuseums;
    private List<Museum> mMuseumsListFull;

    /**
       método 'getItemCount()'
       Retornar o tamanho (quantidade de elementos) do seu conjunto de
       dados (Dataset) que foi chamado (invocado) pelo gerenciador de layout
     */
    @Override
    public int getItemCount(){
        if(mMuseums != null) {
            return  mMuseums.size();
        } else {
            return  0;
        }
    }

    /**
        OnItemClickListener
        Definição de interface para que um retorno de chamada seja obtido quando um
        item nesta exibição (Adapter/View) for clicado. Retorna valor booleano (true/false)

        Interface é a especificação de um comportamento abstrato. É tipo um
        contrato que diz o que uma classe pode fazer sem dizer nada sobre como a classe
        fará isso.

        Vou tentar clarear isso... pense assim... ligue a iluminação do
        seu quarto. Fez isso?

        Você usou uma Interface para isso e nem faz ideia de como a lâmpada
        acendeu. Ok, faz ideia? Tem certeza? Se você usou o interruptor você acabou
        de utilizar uma Interface, ou seja, você foi lá e pressionou o botão do
        interruptor e nem precisou saber que haviam duas posições no interruptor:
        circuito aberto e circuito fechado. Na posição aberta, a energia circula e é
        distribuída; e na posição fechada, este processo é interrompido.

        Um outro exemplo é o seu controle remoto da sua TV. O controle é uma Interface
        entre você e a TV.

        No rigor do termo, uma Interface em Java nada mais é do que uma classe abstrata
        composta somente por métodos abstratos. E como tal, obviamente, não pode ser
        instanciada ( = new ). Ou seja, ela só contém as declarações dos métodos e
        valores constantes, nenhuma implementação, só o 'modelo'.

        Então, para que raios serve uma classe sem implementação?  Ela serve para
        que outras classes, baseadas nessa Interface, implementem esses métodos
        para fins específicos.

        Parece confuso e complicado, mas é como comentado anteriormente sobre
        Interface: ela será uma espécie de comunicação entre meios. Geralmente
        entre o que é pedido (das funções que ela executa) e a implementação.

        Um outro exemplo de Interface em nosso dia-a-dia, é o cardápio (menu) de um
        restaurante. No menu do restaurante tem dizendo todas as refeições existentes
        naquele local, inclusive com os ingredientes. Os clientes que lá forem vão
        pedir uma determinada comida, e querem receber exatamente aquilo que pediram.
        Porém, eles não vão ver e nem se importar de como foi feito o prato, só
        querem receber aquilo que foi solicitado. Então, o cardápio é uma Interface
        de comunicação entre o cliente os cozinheiros.

     */
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(Museum museum);
    }

    /**
      OnLongClickListener
      Definição de interface para que um retorno de chamada seja chamado quando
      uma exibição for clicada e mantida. Retorna um valor booleano (true / false)
     */
    private OnLongClickListener mOnLongClickListener;
    public interface OnLongClickListener{
        void onLongClick(Museum museum);
    }

    /**
       Classe 'MuseumViewHolder'

       Fornece uma referência às visualizações para cada item de dados.
       Importante - Ocorre que para itens de dados complexos poderemos precisar de
       mais de uma visualização por item e você terá que fornece acesso a todas as
       visualizações de um item de dados em um suporte de visualização próprio (exclusivo)

       O padrão View Holder tem como objetivo manter as referências da view ajudando
       a reciclagem de um item na lista de items. Assim, não é necessário procurar
       as referências da view quando for apresentar uma nova view para o usuário.

       Complementando, o RecyclerView é uma 'evolução' da ListView e da GridView
       (componentes presentes desde da primeira versão do Android para se fazer listas e
       grades de conteúdo). O seu uso é recomendado em função: das constantes
       atualizações e melhorias (faz parte da Biblioteca de Suporte v7 do Android);
       da performance, principalmente no reuso da célula (view) ao descer/subir a lista
       com o uso da design-pattern View Holder; das animações, principalmente no uso da
       classe DefaultItemAnimator (classe responsável por animar os conteúdos da lista
       quando forem adicionados, removidos ou alterado sua posição, dando uma melhor
       experuência para o usuário); do gerenciador de layout, que define qual será a
       posição dos itens na lista (se será uma lista horizontal, vertical, uma
       grade e etc) e com essa flexibilidade podemos mudar a disposição dos itens de
       acordo com a configuração do usuário sem a necessidade de recriar toda a estrutura
       do RecyclerView em tempo de execução; atualização apenas do item alterado o que
       faz melhorar a performance em termos de desempenho do aplicativo.

       [Observação] Sem o construtor privado da classe 'MuseumViewHolder' haverá
                    a indicação de erro na construção do código.

     */
    public class MuseumViewHolder extends RecyclerView.ViewHolder {
        // cada item de dados é apenas uma string neste caso e será exibido no TextView
        private TextView text_name_view_holder;
        private TextView text_style_view_holder;
        private TextView text_score_view_holder;

        /**
         Sobre o construtor 'MuseumViewHolder'

         Ao escrever a linha de código: private MuseumViewHolder(final View itemView){ }

         Esteja atento para finalizar com o   }   e só depois codificar dentro das {  }

         Algumas questões sobre o padrão (Pattern) ViewHolder:

         1- O que é o ViewHolder Pattern?
         Resposta : É uma abordagem utilizada para guardar um conjunto de views para que
                    possam ser eficientemente acessadas e reutilizadas, quando necessário.

         2- Qual é a finalidade do ViewHolder Pattern?
         Resposta : A finalidade do padrão ViewHolder é guardar as views, ou seja, evitar o
                    uso repetido de findViewById() para obter as referências às views. Ao serem
                    guardadas num objeto ViewHolder as referencias estão disponíveis de imediato.

         3- Qual é o benefícios ao utilizar ViewHolder Pattern?
         Resposta : Evitar a perda de performance devido ao uso repetido findViewById().

        */
        private MuseumViewHolder(final View itemView){
            super(itemView);
            text_name_view_holder = itemView.findViewById(R.id.text_recycler_name_museum);
            text_style_view_holder = itemView.findViewById(R.id.text_recycler_style_museum);
            text_score_view_holder = itemView.findViewById(R.id.text_recycler_score_museum);

            /**
             onClick() e View.OnClickListener.

             Isso é chamado quando o usuário toca no item (no modo de toque) ou
             atribui foco ao item com as teclas de navegação ou o trackball e
             pressiona a tecla 'enter' ou o pressiona no trackball.
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(mMuseums.get(position));
                    }
                }
            });

            /**
             onLongClick() e View.OnLongClickListener

             Isso é chamado quando o usuário mantém o item pressionado (no
             modo de toque) ou atribui foco ao item com as teclas de navegação
             ou o trackball e mantém pressionada a tecla 'enter' ou o
             trackball (por um segundo).
             */
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(mOnLongClickListener != null && position != RecyclerView.NO_POSITION){
                        Toast.makeText(v.getContext(), "OK", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });

            /**
             Outros eventos inclusos na interface do Listener

             onFocusChange() e View.OnFocusChangeListener
             Isso é chamado quando o usuário navega para ou do item usando as
             teclas de navegação ou o trackball.

             onKey() e View.OnKeyListener
             Isso é chamado quando o usuário está com foco no item e pressiona ou
             solta uma tecla de hardware no dispositivo.

             onTouch() e View.OnTouchListener.
             Isso é chamado quando o usuário realiza uma ação qualificada como
             um toque de evento, incluindo o pressionamento, a liberação ou
             qualquer outro gesto de movimento na tela (dentro dos limites do item).

             onCreateContextMenu() e View.OnCreateContextMenuListener.
             Isso é chamado quando um menu de contexto está sendo construído
             (como resultado de um “clique longo”)

             Leia mais em
             https://developer.android.com/guide/topics/ui/ui-events?hl=pt-br
             https://developer.android.com/guide/topics/ui/ui-events?hl=pt-br#EventListeners
             */
        }
    }

    /**
        método 'onCreateViewHolder()'
        Criar suporte para novas visualizações (chamadas pelo gerenciador de layout)
        A visualização 'itemView' utiliza-se do recurso de
        layout 'recyclerview_item_museums' quando este é chamado o infla, ou seja,
        fazer aparecer. Holder é um suporte, é a base para a visualização

        O gerenciador de layout chama o método 'onCreateViewHolder()' do adaptador.
        Esse método precisa construir um RecyclerView.ViewHolder e definir a
        visualização que ele usa para exibir o conteúdo. O tipo de ViewHolder
        precisa corresponder ao tipo declarado na assinatura da classe Adapter.
        Normalmente, ele define a visualização por meio da inflação de um arquivo
        de layout XML. Como o fixador de visualização ainda não foi atribuído a
        nenhum dado específico, o método 'onCreateViewHolder' não define o
        conteúdo da visualização por si só. Para isso acontecer é necessário
        o método 'onBindViewHolder()'
     */
    @Override
    public MuseumViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // cria uma nova visualização
        View itemView = mInflater.inflate(R.layout.recyclerview_item_museums, parent, false);
        // retorna a visualização
        return new MuseumViewHolder(itemView);
    }

    /**
        método 'onBindViewHolder()'
        Substituir o conteúdo de uma visualização (chamada pelo gerenciador de layout)
        Bind é a ligação

        Após funcionar o método 'onCreateViewHolder()' o gerenciador de layout
        vincula (liga) o fixador de visualização a seus dados. Ele faz isso chamando
        o método 'onBindViewHolder()' do adaptador, passando a posição do fixador
        de visualização em RecyclerView. O método 'onBindViewHolder()' precisa
        buscar os dados apropriados e usá-los para preencher o layout do fixador de
        visualização. Por exemplo, se RecyclerView estiver exibindo uma lista de
        nomes, o método poderá encontrar o nome apropriado na lista e preencher o
        widget TextView do fixador de visualização.
     */
    @Override
    public void onBindViewHolder(MuseumViewHolder holder, int position){
        // get - obtém o elemento (current) do seu conjunto de dados nesta posição (position)
        Museum current = mMuseums.get(position);
        // substitui o conteúdo da visualização principal (holder) por esse elemento (current)
        holder.text_name_view_holder.setText(current.getName());
        holder.text_style_view_holder.setText(current.getStyle());
        // causa erro     necessário valueOf       holder.text_score_view_holder.setText(current.getScore());
        holder.text_score_view_holder.setText(String.valueOf(current.getScore()));

        // para a imagem deve ser feito aqui ;-)
    }

    /**
     método 'setMuseums()'

     Será utilizado na classe MuseumsActivity juntamente com
     um adapter para definir (setar/vincular) os dados a serem exibidos na
     atividade uma vez que haverá nela um método observando a lista de
     dados (no caso os museus, se houverem)

     Aqui obtemos a lista de museus e criamos uma lista completa de museus para
     ser utilizada quando deixamos de fazer a busca por um museu utilizando
     parte do nome de um museu

     Por fim, notificamos que há uma mudança no conjunto de dados apresentados
     com o uso do método 'notifyDataSetChanged()'
     */
    public void setMuseums(List<Museum> museums){
        mMuseums = museums;
        mMuseumsListFull = new ArrayList<>(museums);
        notifyDataSetChanged();
    }

    /**
     método 'getMuseumAt()'

     Será utilizado na classe MuseumsActivity juntamente com
     um adapter para obter (get) a posição de um item na lista do RecyclerView
     que será deslizado (swip) para esquerda ou direita eliminado este item
     da lista
     */
    public Museum getMuseumAt(int position){
        return mMuseums.get(position);
    }

    /**
     método 'setOnItemClickListener()'

     Será utilizado na classe MuseumsActivity juntamente com
     um adapter para iniciar uma outra atividade onde será possível EDITAR os dados
     do museu para uma correção ou até mesmo observar mais detalhes que não são
     apresentados na lista do RecyclerView
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    /**
     método 'setOnLongClickListener()'

     Sem utilização até o momento. Implementação feita para atender a
     questão didática para reforçar o conceito de Interface
     */
    public void setOnLongClickListener(OnLongClickListener listener){
        this.mOnLongClickListener = listener;
    }

}

//linha 499 e 500