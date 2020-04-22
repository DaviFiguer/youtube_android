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

import androidx.appcompat.app.AppCompatActivity;

/**
 Considerações Iniciais

 [1] Activities no Android são a grosso modo a representação de uma tela. É por meio dela que o
     conteúdo das telas é exibido e as interações (ações) de interface são executadas. Dentre os
     principais componentes do Android, é o que corresponde à UI (View) dos aplicativos.

 [2] Para que a nossa Activity esteja disponível no launcher do dispositivo, na forma de um ícone
     na lista de aplicativos, precisamos configurar o 'AndroidManifest.xml', um arquivo que
     corresponde a uma espécie de índice ou cartão de visita do nosso aplicativo. Nele é que serão
     descritas, dentre outras informações, quais Activities nosso aplicativo possui e quais
     permissões ele necessita para ser executado.

     A seguinte inclusão é necessária no código do 'AndroidManifest.xml'

     <activity android:name=".MuseumAddEditActivity"
                android:parentActivityName=".MuseumsActivity" />

     Lembrete : uma inclusão para permitir a intenção de uso recurso de busca/pesquisa (SEARCH) na
     atividade 'MuseumsActivity' que deverá ser feita também no 'AndroidManifest.xml'

     <activity android:name=".MuseumsActivity">
         <intent-filter>
            <action android:name="android.intent.action.SEARCH" />
         </intent-filter>
     </activity>

 [3] O SO Android possui um mecanismo de troca de mensagens conhecido como intents. Uma intent
     indica a intenção de se fazer alguma coisa, seja enviar uma mensagem para um componente ou
     iniciar um deles. Essa intent pode conter diversas informações, como um destinatário, uma
     ação (action), uma categoria (category) e outras várias informações extras.

 [4] Nesta classe extendemos a classe 'AppCompatActivity'. A 'AppCompatActivity' é uma versão da
     Activity que garante um comportamento uniforme a partir da versão 7 (2.1) do Android. Essa
     classe faz parte das bibliotecas de suporte.

     Como estendemos 'AppCompatActivity', herdamos vários métodos que nos permitem fazer diversas
     coisas no SO Android. O método 'onCreate()' é um dos callbacks do ciclo de vida de uma
     Activity.

     Para navegar entre as fases do ciclo de vida da atividade, a classe 'Activity' fornece um
     conjunto principal de seis callbacks: onCreate(), onStart(), onResume(), onPause(), onStop() e
     onDestroy(). Conforme a atividade entra em um novo estado, o sistema invoca cada um desses
     callbacks.

     À medida que o usuário navega no aplicativo, sai dele e retorna a ele, as instâncias
     'Activity' no aplicativo transitam entre diferentes estados no ciclo de vida. A classe
     'Activity' fornece uma quantidade de callbacks que permite que a atividade saiba sobre a
     mudança do estado: informa a respeito da criação, interrupção ou retomada de uma atividade ou
     da destruição do processo em que ela reside por parte do sistema.

     'Activity' é a classe base 'genérica' para todas as outras activities (o que inclui a própria
     'AppCompatActivity'. Já AppCompatActivity é um tipo mais 'específico', cuja característica
      principal é prover suporte aos recursos de 'ActionBar', alternância interna entre temas
      claros e escuros com o uso do 'Theme.AppCompat.DayNight'.

 [5] Temos a presença do método 'saveMuseum()' criado a partir de refatoração de código, ou seja,
     uma técnica que permite ao programador que ao notar a repetição de uma mesma lógica em dois
     ou mais locais diferentes do código faça uso de um método ou até mesmo crie uma nova classe.
     A refatoração é o processo de modificar um sistema de software para melhorar a sua estrutura
     interna sem modificar (alterar) seu comportamento externo. O uso desta técnica aprimora a
     concepção ou o projeto de um software e evita a sua deterioração tão comum durante o ciclo de
     vida de um código. Que fique bem claro : deterioração = 'puxadinhos'.

     De acordo com Martin Fowler (https://martinfowler.com/) refatoração "é uma técnica
     disciplinada de limpar e organizar o código, e por consequência minimizar a chance de
     introduzir novos bugs".

     Em um ambiente em que a equipe de programação é grande, menor é o contato entre os
     programadores. Tal fato poderá levar a pequenos deslizes, como código repetido, classes em
     pacotes errados, métodos localizados em classes erradas, etc. Isso pode contribuir para
     uma estrutura de código um pouco bagunçada, e até confusa. Compreender o que foi feito pode
     demandar um certo gasto de tempo. Pior será para implementar uma nova funcionalidade.

     Algumas vantagens da refatoração:

     (a) melhora o projeto - programadores que nunca viram o código ao implementar uma nova
         funcionalidade irão respeitar o padrão utilizado evitando-se assim a deterioração do
         código ao longo do tempo.

     (b) torna o software fácil de entender - ao fazer uso algumas "sujeiras" e/ou erros muito
         provavelmente serão removidas do código, o que torna o código mais fácil de ser
         compreendido e mantido por outro(s) programador(es). A tal "sujeira" pode ser: não nomear
         as variáveis corretamente, não comentar, duplicar código, colocar métodos em classes
         inadequadas.

     (c) ajuda a encontrar bugs - quando o programador vai refatorar o código, ele precisa
         entender muito bem o que aquele código está fazendo. Ao entender muito bem o funcionamento,
         os possíveis bugs existentes naquele código acabam se tornando evidentes. E como já
         estamos trabalhando no código, recomenda-se também eliminar o bug.

     (d) ajuda a programar mais rápido - um programador que possui o domínio da técnica de
         refatoração eleva a qualidade do software onde o código fica mais fácil de entender. Isso
         contribui para o programador (ou outro) ao pegar o código para adicionar uma nova
         funcionalidade fazê-la de modo rápido e tranquila.

 [6] Entender o código de outra pessoa não é uma tarefa trivial e/ou fácil. Ainda mais quando é um
     software (código) muito grande e complexo. Fato - quando uma pessoa modifica um código que não
     é de sua autoria poderá ocorrer alguns deslizes e com isso o código passa a ser mais difícil
     de ser compreendido. Portanto, ao fazer uso de códigos prontos de terceiros, esteja preparado
     para assumir certos riscos e principalmente 'gastar seu tempo' para compreender a lógica.

     E, sendo bem honesto, não basta apenas ir até o https://stackoverflow.com/questions, o
     https://www.youtube.com/ ou o https://www.google.com/ que tudo estará resolvido. Programar
     exige que você:

     (i) Tenha um bom raciocínio lógico - este é o ponto, o programador resolve problemas
         utilizando código para transcrever um raciocínio lógico.

     (ii) Seja autodidata - não imagine que apenas cursos on-line ou off-line irão lhe transformar
          em um programador. A atualização é constante.

     (iii) Goste de resolver desafios (ou problemas mesmo) - programação é 80% do tempo resolução
           de problemas e isso não é um exagero. A complexidade é uma constante na vida de um
           programador. Já ouviu a expressão "pena de pato"? O pato produz um óleo que repele a
           água, obviamente, que esse óleo vai parar em suas penas, o que evita que ele fique
           molhado enquanto nada. Vamos ao problema: você é o pato e o problema é a água, você
           vai escapar do problema ou irá enfrentá-lo?

     (iv) Saiba o idioma inglês - o conhecimento escrito em nosso idioma nativo, o português
          brasileiro, neste segmento (ou será em todos?) é bem restrito, porém, haverá aqueles
          que discordem disso, eu respeito.

     (v) Goste de aprender - seja lendo, pesquisando, investigando, desvendando, duvidando, etc.

     (vi) Tenha bom nível de concentração - procure ambientes que ofereçam menos distrações,
          pratique meditação, faça exercícios físicos, faça listas, use mais sua memória,
          divida tarefas grandes em várias partes, mantenha-se bem alimentado durante o dia,
          ouça música que você já conheça (monte uma playlist de 50 minutos e faça uso do fone
          de ouvido), elimine a bagunça e o desconforto.

     (vii) Seja curiosa ou curioso.

     (viii) Seja disciplinado - faça as coisas rotineiramente, da mesma forma por diversas vezes,
            tenha a capacidade de seguir padrões.

     (ix) Tenha diposição para estudar sempre, mesmo não sendo nas áreas do seu interesse.

     (x) Domine o que é importante - ter o conhecimento sobre o assunto em que se está trabalhando
         é fundamental para que se programe com clareza do resultado esperado.

     (xi) Seja 3Ps (paciente, perseverante, persistente).

     (xii) Sempre desconfie que está errado, ou, que existe uma maneira ainda melhor de resolver
          o problema.

     (xiii) Saiba ouvir os mais experientes e os que sabem menos que você também, sempre há algo
           novo que se possa aprender.

     (xiv) Não precisa ser 10 ou 'top' em Matemática, afinal, algoritmo não tem a ver com
            logaritmo.

     (xv) Tenha ética.

     (xvi) Troque 'figurinhas' e faça networking com outros programadores - com certeza alguém já
          passou pelo mesmo problema que você, então por que não buscar ajuda? Buscar ajuda,
          explicação, debater, não é querer a 'resposta pronta'.

     (xvii) Seja organizado - isso vale para todas as áreas de sua vida (relacionamento, estudo,
           trabalhos, projetos)

     (xviii) Tenha a visão de tudo (visão macro) - tenha a capacidade de abstrair os problemas em
             partes ou pedaços (módulos) e como eles se conectam. Um outro nome para essa visão é
             'visão sistemica'.

  [7] Técnica para localizar erros com o uso do Logcat

      O Logcat é uma ferramenta de linha de comando que despeja um registro de mensagens do
      sistema, incluindo stack traces, quando o dispositivo gera um erro, e mensagens que você
      escreveu no app com a classe Log. Com essa classe, temos melhores mecanismos para controlar
      o nível do log (error, warning, debug etc) e podemos adicionar uma tag (primeiro parâmetro)
      para que fique mais fácil de encontrar o que queremos. Lembra-se do System.out do Java?

      Exemplos de uso:

      Log.d("MA::Action", intent.getAction());
      Log.d("MA::Category", category);
      Log.d("MA::Component", intent.getComponent().getClassName());

 [8] Para saber mais sobre :

     O ciclo de vida da atividade
     https://developer.android.com/guide/components/activities/activity-lifecycle.html

     Gravar e visualizar registros com o Logcat
     https://developer.android.com/studio/debug/am-logcat?hl=pt-br

     Referências para o desenvolvedor sobre a classe 'AppCompatActivity'
     https://developer.android.com/reference/androidx/appcompat/app/AppCompatActivity

 [9] Leitura recomendada:

     https://martinfowler.com/articles/class-too-large.html
     https://bit.ly/3eFrWVt
     https://devsamurai.com.br/5-caracteristicas-de-um-bom-programador/

 */


/**
 Classe 'MuseumAddEditActivity'

 Esta classe é a responsável por definir a View que representa dois estados de funcionamento:

 (a) novo museu, ou seja, apresenta uma visualização vazia permitindo ao usuário informar
 o nome do museu, o estilo do museu e a avaliação do museu. Há uma validação de dados
 para que obrigatoriamente seja informado o nome do museu e o estilo do museu. Os valor
 mínimo (0) e máximo (10) para a avaliação é feito de modo programatico, ou seja, no
 código e talvez isso não seja uma boa prática, porém, exige uma melhor avaliação.

 (b) edição do museu, ou seja, em uma atividade anterior o usuário observa algum erro e deseja
 realizar a alteração nos dados informados.

 Essa classe estende a classe 'AppCompatActivity'
 */

public class MuseumAddEditActivity extends AppCompatActivity {

}
