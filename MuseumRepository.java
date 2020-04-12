package com.example.agendiario;

/**
 Considerações Iniciais

 [1] UI (User Interface – ou interface do usuário) – é tudo aquilo que é perceptível
     visualmente em alguma plataforma e leva o usuário a uma interação positiva. Pode ser um
     botão, um menu diferente ou até mesmo um som.

 [2] UX (User Experience - ou experiência do usuário) - é a parte intangível, que identifica
     os 'pontos de contato' ou 'algo que faz bem' que fazem parte de uma boa experiência
     para o usuário. Recomendo a leitura do livro 'Não me faça pensar' de Steve Krug.

 [3] A experiência do usuário de aplicativos para dispositivos móveis

     Na maioria dos casos, as aplicações para computador têm um único ponto de entrada
     em uma área de trabalho ou um acesso rápido de programas e, em seguida, são executados
     como um único processo monolítico. Por outro lado, os aplicativos Android possuem uma
     estrutura muito mais complexa. Em geral, um aplicativo Android contém vários componentes
     como atividades, fragmentos, serviços, provedores de conteúdo e broadcast receivers.

     A maioria desses componentes do aplicativo é declarada no arquivo de manifesto do
     do aplicativo. O Sistema Operacional (SO) Android usa esse arquivo (AndroidManifest.xml)
     para decidir como integrar seu aplicativo à experiência geral do usuário do dispositivo.
     Considerando que um aplicativo Android corretamente programado contém vários componentes e
     que os usuários geralmente interagem com os mais variados aplicativos em um curto período
     de tempo, os aplicativos precisam se adaptar a diferentes cenários e tipos de fluxo de
     trabalho e outras tarefas controlados pelo usuário.

     Por exemplo, pense no que acontece quando você compartilha uma foto no seu aplicativo
     de rede social favorito (me segue lá no Instagram @1268marcos).

     O aplicativo aciona um intent de câmera (intenção de uso). Em seguida, o SO Android abre
     um aplicativo de câmera para lidar com a solicitação feita. Nesse momento, o usuário sai
     do aplicativo de rede social, mas a experiência dele é contínua (fazer n-coisas ao mesmo
     tempo).

     O aplicativo de câmera pode acionar outros intents, como a abertura do seletor de
     arquivos (a popular Galeria de Imagens), que pode iniciar mais um outro aplicativo.

     Por fim, o usuário retorna ao aplicativo de rede social e compartilha a foto.

     É isso que acontece com a experiência do usuário. Imagine se ele receber um match no
     no Tinder durante este compartilhamento de foto. Para complicar, a qualquer momento
     durante o processo, o usuário pode ser interrompido por uma chamada telefônica ou
     notificação. Depois de lidar com essa interrupção, o usuário espera retomar o processo de
     compartilhamento de fotos. Esse comportamento de alternância de aplicativos é comum em
     dispositivos móveis, e seu aplicativo precisa lidar com esses fluxos corretamente.

     Lembre-se de que os recursos de dispositivos móveis também são limitados. Por isso, o
     sistema operacional pode interromper os processos de alguns aplicativos a qualquer momento
     para dar espaço a outros novos. O sistema operacional pode fazer isso? Sim, ele pode e
     vai fazer.

     Considerando as condições desse ambiente, é possível que os componentes do seu aplicativo
     sejam iniciados individualmente e fora de ordem, e eles podem ser destruídos a qualquer
     momento pelo usuário ou pelo sistema operacional. Como esses eventos não estão sob seu
     controle, não é recomendável armazenar nenhum dado ou estado do aplicativo nos componentes
     do seu aplicativo e não permita que os componentes dele dependam uns dos outros. Para essa
     não-dependência damos o nome de 'Separação de Conceitos'.

     É um erro comum escrever em sua totalidade o código em Activity ou Fragment. Essas classes
     baseadas em UI devem conter apenas a lógica que processa as interações entre a UI e o
     sistema operacional. Ao manter essas classes o mais enxutas possível (com menos código
     possível), você pode evitar muitos problemas relacionados ao ciclo de vida do aplicativo.

     Lembre-se de que você não possui implementações de Activity e Fragment. Na verdade, elas
     são apenas classes que representam o contrato entre o SO Android e seu aplicativo. O SO
     pode destruí-las a qualquer momento com base nas interações do usuário ou devido a
     condições do sistema, como pouca memória. Para oferecer uma experiência do usuário
     satisfatória e uma experiência de manutenção de aplicativo mais gerenciável, o melhor a se
     fazer é minimizar sua dependência delas.

     A experiência do usuário para o aplicativo 'AGENDIARIO' tem como referência o modelo de
     arquitura de aplicativo recomendada nos 'Princípios de Arquitetura Comuns' disponível
     em https://developer.android.com/jetpack/docs/guide#common-principles

 */

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 Classe 'MuseumRepository'

 A classe do repositório será responsável por interagir com o banco de dados
 (padrão Room) fazendo uso da classe 'MuseumViewModel' e precisará fornecer métodos que usem
 a interface 'MuseumDao' para inserir, excluir e consultar registros na tabela (entidade)
 'museums' definida na classe 'Museum'.

 Módulos de repositório manipulam operações de dados. Eles disponibilizam uma API limpa para
 que o restante do aplicativo possa recuperar esses dados com facilidade. Eles sabem onde coletar
 os dados e quais chamadas de API precisam ser feitas quando os dados são atualizados. Você pode
 considerar os repositórios como mediadores entre fontes de dados diferentes, por exemplo,
 modelos persistentes, serviços da Web e caches.

 Com exceção do método 'getAllMuseums()' presente na classe 'MuseumRepository' que faz uso
 do 'padrão Dao' onde retorna um objeto do tipo LiveData. Os outros métodos da classe
 'MuseumRepository' que formam a sua API realizarão as outras operações do banco de dados e
 deverão ser executadas em threads separados do thread principal usando a classe 'AsyncTask'.

 Quatro perguntas surgem aqui! 1- O que é uma API?, 2- O que é um Livedata?, 3- O que é uma (um)
 thread? e 4- O que é a classe AsyncTask?

 Resposta para a pergunta 1 : O que é uma API?

 API (Application Programming Interface ou Interface de Programação de Aplicação), é uma
 coleção de rotinas e padrões estabelecidos por um software para a utilização das suas
 funções/funcionalidades por outros aplicativos que não pretendem envolver-se em detalhes da
 implementação do software (codificação), mas apenas usar seus serviços.

 De modo geral, a API é composta por uma série de funções acessíveis somente por programação, e
 que permitem utilizar características do sistema operacional e até mesmo do aplicativo
 que se está desenvolvendo e que são menos evidentes. Uma API não é acessível ao usuário do
 aplicativo.

 Por exemplo, um sistema operacional possui uma grande quantidade de funções na API, que permitem
 ao programador criar Views, acessar arquivos na galeria de fotos, realizar chamadas telefônicas,
 e etc. Mas as APIs dos sistemas operacionais costumam ser dissociadas de tarefas mais essenciais,
 como a manipulação de blocos de memória e acesso a dispositivos. Essas tarefas são atributos do
 núcleo de sistema e raramente são programáveis.

 Mais recentemente, o uso de APIs tem se generalizado nos plugins (acessórios que complementam a
 funcionalidade de um programa). Os autores do programa principal fornecem uma API específica
 para que outros autores criem plugins, estendendo as funcionalidades do programa.

 O aplicativo 'AGENDIARIO' vai precisar várias APIs, por exemplo, API para armazenar os dados
 dos usuários do aplicativo em serviço na nuvem e API para gerar relatórios de análise de dados.

 Resposta para a pergunta 2 : O que é um Livedata?

 Basicamente, LiveData é uma classe capaz de conter dados que passamos à ela, para serem
 observados. Esta classe é tão simples, ela só tem uma função que é notificar a interface
 LifecycleOwner e seu único método getLifecycle() sobre alterações que ocorrem nos dados.

 É uma classe armazenadora de dados observáveis. Diferente de um observável comum, o LiveData
 conta com reconhecimento do ciclo de vida, ou seja, ele respeita o estado do ciclo de vida de
 outros componentes do aplicativo, como atividades (activity), fragmentos (fragments) ou
 serviços (services). Esse reconhecimento garante que o LiveData atualize apenas os
 observadores de componente do aplicativo que estão em um estado ativo no ciclo de vida. O
 LiveData inclui lógica de limpeza para evitar o vazamento de objetos e o consumo excessivo
 de memória.

 O LiveData considera que um observador, que é representado pela classe Observer, encontra-se
 em um estado ativo se o ciclo de vida dele estiver no estado STARTED ou RESUMED. O LiveData
 só notifica observadores ativos sobre atualizações. Observadores inativos registrados para
 observar objetos LiveData não são notificados sobre mudanças.

 O uso do LiveData oferece as seguintes vantagens: garantia de que a interface do usuário (IU)
 corresponde ao estado dos dados; SEM VAZAMENTO DE MEMÓRIA onde a limpeza da memória ocorre
 quando o ciclo de vida associado é destruído; sem falhas causadas por atividades
 interrompidas pense em quantos apps estão abertos agora na memória e quantos estão inativos;
 sem gerenciamento manual do ciclo de vida; mudanças de configuração apropriadas como exemplo
 a rotação do dispositivo; compartilhamento de recursos.

 Resposta para a pergunta 3 : O que é uma (um) thread?

 Basicamente, thread representa uma instância da CPU da máquina virtual Java, e que tem
 associada um trecho de código que será executado, e uma área de memória.

 Pense numa thread como uma sequência de comandos sendo executados em um programa. Se
 você tiver duas threads, terá duas sequências de comandos executando ao mesmo tempo no
 mesmo programa ou processo.

 Note que executar o mesmo programa duas vezes não é criar mais threads e sim criar dois
 processos do mesmo programa, chamamos isso de contexto de execução. Com isso, as variáveis
 e funções de uma thread podem ser acessadas também pelas outras threads, já que a
 memória de um processo não é comportilhada por outros processos, ou seja, é mais difícil
 fazer dois processos conversarem. Com threads isso fica mais simples, uma vez que a threads
 rodam concorrentemente num mesmo processo. Os processos executam concorrentemente no
 sistema operacional. Então, por que os sistemas operacionais modernos permitem programar
 em threads além de processos? Porque além de tornar mais simples a programação
 multitarefas isso é mais eficiente em termos de uso de memória.

 O uso de threads começa a ficar interessante quando você quer executar pelo menos duas
 coisas ao mesmo tempo em um programa para tirar vantagem da múltiplas CPUs ou ainda para
 evitar que o programa inteiro fique travado ao executar uma operação demorada. Pense
 que você está no grupo da família (200 membros) no Whatsapp e alguém resolve enviar uma
 mensagem; graças ao uso de threads não temos que esperar todos da família receberem a
 mensagem para que o Whatsapp fique disponível para você mandar a pergunta para o seu colega
 de turma 'ô zeehhh vai ter prova amanhã????'.

 Resposta para a pergunta 4 : - O que é a classe AsyncTask?

 AsyncTask é uma das classes que implementa concorrência no Android e, basicamente, auxilia
 outras classes como a Thread. A 'concorrência' é um paradigma de programação usado na
 construção de programas que fazem uso da execução simultânea de diversas tarefas que
 podem ser implementadas como programas separados ou como um único programa que dispara
 várias linhas de execução ao mesmo tempo.

 Atualmente, qualquer evento ou modificação que ocorre em um aplicativo, é gerenciado na Main
 Thread do OS (Thread principal). Isso significa que, por exemplo, se você realizar uma operação
 de longa duração no aplicativo, como por exemplo, fazer o download de um arquivo, isso vai fazer
 com que a Main Thread seja travada até que sua ação esteja completa. Ou seja, o seu aplicativo
 vai ficar travado, o usuário não vai conseguir fazer nada nele, e só vai destravar quando o
 download do arquivo for concluído. Tal situação é prejudicial à experiência do usuário.

 Uma AsyncTask evita justamente isso. Ela permite que você crie instruções no background e
 sincronize essas instruções com a Thread Principal, ou seja, o usuário vai poder continuar
 utilizando o aplicativo normalmente, ele não será travado e você também poderá atualizar a UI
 ao decorrer do processo.

 Basicamente, essa classe não interfere na Thread Principal. Para saber mais sobre a classe
 AsyncTask leia em https://developer.android.com/reference/android/os/AsyncTask

 */

public class MuseumRepository {

    private MuseumDao mMuseumDao;
    private DbRoomDatabase mDbRoomDatabase;
    private LiveData<List<Museum>> mAllMuseums;

    LiveData<List<Museum>> getAllMuseums(){
        return mAllMuseums;
    }

    public MuseumRepository(Application application) {
        mDbRoomDatabase = DbRoomDatabase.getDatabase(application);
        mMuseumDao = mDbRoomDatabase.mMuseumDao();
        mAllMuseums = mMuseumDao.getAllMuseums();
    }

    /**
     Classe estendida 'AsyncTask', seus argumentos e usos

     Uma AsyncTask (tarefa assíncrona) é definida por uma computação que é executada em um
     encadeamento em segundo plano e cujo resultado é publicado no encadeamento da interface do
     usuário. Uma tarefa assíncrona é definida por 3 tipos genéricos de argumentos (ParamsType,
     ProgressValue e ResultValue) e 4 métodos de chamada (etapas): onPreExecute(),
     doInBackground(), onProgressUpdate() e onPostExecute().

     A classe 'AsyncTask' será estendida para criar as subclasses:

     - 'InsertAsyncTask',
     - 'UpdateAsyncTask',
     - 'DeleteAsyncTask' e
     - 'DeleteAllMuseumsAsyncTask'

     Nestas subclasses faremos a substituição do método 'doInBackground()' que será
     sobreescrito com a anotação '@Override' para que realize as operações com o banco
     de dados e retorne o valor nulo.

     Os 3 argumentos da AsyncTask<ParamsType, ProgressValue, ResultValue> { }

                  ParamsType: É o tipo do parâmetro que será enviado para a instrução. Este
                  parâmetro será enviado ao método doInBackground(). Vamos indicar que
                  será a classe 'Museum'

                  ProgressValue: Como o próprio nome diz, é o tipo do valor do progresso da
                  nossa instrução. Ele mostrará o progresso atual do que estamos tentando
                  realizar. Definiremos como sendo a classe de espaço reservado 'Void' para
                  manter a referência com a classe atual. O uso do 'Void' representa um objeto
                  nulo como argumento.

                  ResultValue: É o tipo do resultado final que receberemos na nossa instrução,
                  aqui representada por operações junto ao banco de dados (insert, update,
                  delete). É o resultado como tudo ocorreu. Seu valor é retornado do método
                  'doInBackground()'. Utilizaremos a classe de espaço reservado 'Void'. O uso
                  do 'Void' representa um objeto nulo como argumento.

     Os 4 métodos de chamada na sequencia em que ocorrem estas etapas da AsyncTask:

     (I) 'onPreExecute()' - é chamado (invocado) no encadeamento da interface do usuário antes
         da tarefa ser executada. Esta etapa é normalmente usada para configurar a tarefa,
         por exemplo, mostrando uma barra de progresso na interface do usuário. Não faremos a
         implementação do método 'onPostExecute()' em nossa classe 'MuseumRepository'

     (II) 'doInBackground()' - é responsável por duas coisas. A primeira, ele é responsável
          por receber o tipo de parâmetro que você deseja ter como resultado e a segunda, ele
          é responsável pelo código que irá executar a nossa instrução. Por exemplo, se
          desejamos fazer um HttpRequest, esse método ficará responsável pelo bloco de código
          do HttpRequest e, finalmente, após executar e concluir o nosso código, ele vai
          retornar um valor, que é o resultado que desejamos ver. Se queremos como resultado
          uma String, ele retornará uma String que será enviada ao método 'onPostExecute()'.

          Uma característica do método 'doInBackGround()' é que ele elimina exceções (falhas).

          Depois de muita codificação até aqui talvez você não deva recordar que na classe
          'MuseumDao' a anotação '@Delete int deleteMuseum(Museum museum);' nos indica como
          resultado da exclusão um número inteiro que será enviado ao método 'onPostExecute()'.

     (III) 'onProgressUpdate()' - é chamado (invocado) no thread da interface do usuário após
           uma chamada para apresentar o progresso do que esta sendo executado na outra thread.
           O tempo da execução é indefinido. Este método é usado para exibir qualquer forma
           de progresso na interface do usuário enquanto ocorre outra computação em segundo
           plano que ainda está em execução. Por exemplo, ele pode ser usado para animar uma
           barra de progresso ou mostrar logs em um campo de texto. Não faremos a implementação
           do método 'onPostExecute()' em nossa classe 'MuseumRepository'

     (IV) 'onPostExecute()' - é chamado na sequencia para a interface do usuário após o término
          da computação no método 'doInBackground()', ou seja, quando tudo estiver completo e
          finalizado no método 'doInBackground()', quando já tivermos recebido o parâmetro que
          desejamos receber, o método 'onPostExecute()' será chamado e ele irá mostrar o
          resultado ao usuário, se caso você desejar. Ele recebe o parâmetro que você definiu
          na classe.

          Não faremos a implementação do método 'onPostExecute()' em nossa classe
          'MuseumRepository', porém, segue um exemplo:

            private static class MTask extends AsyncTask<String, Void, String> {
                @Override
                protected String doInBackground(String... result) {
                    // Http....
                    return mHttpResponse;
                }

                @Override
                protected void onPostExecute(String resultValue) {
                    new Toast.makeText(context, resultValue, Toast.LENGTH_SHORT).show();
                }
            }

     */

    private static class InsertAsyncTask extends AsyncTask<Museum, Void, Void> {
        private MuseumDao mAsyncTaskDao;

        /**
         Construtor da subclasse 'InsertAsyncTask()'

         Um construtor de classe pode ser utilizado para suportar a inicialização de
         valores internos da classe durante a declaração de objetos.

         Utilizamos no construtor 'InsertAsyncTask' a classe 'MuseuDao' e o objeto que
         representa esta classe e que foi nomeado como 'dao'. Atribuímos ao membro da classe
         'mAsyncTaskDao' o objeto 'dao'

         */
        InsertAsyncTask(MuseumDao dao){
            mAsyncTaskDao=dao;
        }

        @Override
        protected Void doInBackground(final Museum... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Museum, Void, Void>{
        private MuseumDao mAsyncTaskDao;

        UpdateAsyncTask(MuseumDao dao){
            mAsyncTaskDao=dao;
        }

        @Override
        protected Void doInBackground(final Museum... params){
            mAsyncTaskDao.updateMuseum(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Museum, Void, Void>{
        private MuseumDao mAsyncTaskDao;

        DeleteAsyncTask(MuseumDao dao){
            mAsyncTaskDao=dao;
        }

        @Override
        protected Void doInBackground(final Museum... params){
            mAsyncTaskDao.deleteMuseum(params[0]);
            return null;
        }
    }

    private static class DeleteAllMuseumsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MuseumDao mAsyncTaskDao;

        DeleteAllMuseumsAsyncTask(MuseumDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllMuseums();
            return null;
        }
    }

    //webservice vai tambem aqui, lógico se sua aplicação fizer uso de webservice

    /**
     Métodos de callbacks 'insert()' 'update()' 'delete()' 'deleteAll()'

     Esses métodos tem como argumento o objeto 'museum' que representa a classe 'Museum',
     ou seja, este argumento é um museu que existe no mundo real.

     A funcionalidade destes métodos é instanciar a subclasse correspondente fazendo uso do
     membro 'mMuseumDao' que é objeto do tipo interface de 'MuseumDao' e que executará a tarefa
     com o argumento (parâmetro) 'museum', um objeto representante de museu existente no
     mundo real (por exemplo, MASP)

     O método 'deleteAll()' será utilizado na classe 'MuseumViewModel'

     */

    public void insert(Museum museum){
        new InsertAsyncTask(mMuseumDao).execute(museum);
    }

    public void update(Museum museum){
        new UpdateAsyncTask(mMuseumDao).execute(museum);
    }

    public void delete(Museum museum){
        new DeleteAsyncTask(mMuseumDao).execute(museum);
    }

    public void deleteAll(){
        new DeleteAllMuseumsAsyncTask(mMuseumDao).execute();
    }


}
