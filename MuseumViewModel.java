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

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 Consideraçoes Iniciais

 [1] Ao estender a classe 'AndroidViewModel' para a classe 'MuseumViewModel'. O código irá
     apresentar erro até que seja feita a codificação do constutor da classe 'MuseumViewModel'

 [2] Sobre a ViewModel - A responsabilidade da ViewModel no contexto do padrão MVVM, é
     disponibilizar para a View uma lógica de apresentação. A ViewModel não tem nenhum
     conhecimento específico sobre o que acontece na view, ou como ela é implementada e nem o
     seu tipo. A ViewModel implementa propriedades e métodos (comandos), para que a View possa
     preencher seus controles e notifica a mesma, caso haja alteração de estado; seja através
     de eventos ou notificação de alteração.

     A ViewModel é peça fundamental no padrão MVVM, por que é ela quem vai coordenar as
     iterações da View com o Model funcionando como um intermediário entre elas (em nosso
     exemplo será a 'MuseumsActivity'), haja visto que, ambos não tem conhecimento um do
     outro. E além de tudo isto, a ViewModel, também pode implementar a lógica de validação,
     para garantir a consistência dos dados (não a faremos aqui e sim na atividade).

     Um objeto ViewModel fornece os dados para um componente de interface de usuário (View)
     específica, como um fragmento ou atividade, e contém lógica de negócios de manipulação de
     dados para se comunicar com o modelo. Por exemplo, o ViewModel pode chamar outros componentes
     para carregar os dados e pode encaminhar solicitações de usuários para modificar os
     dados. O ViewModel não sabe sobre componentes de interface de usuário, por isso não é
     afetado por alterações de configuração, como recriar uma atividade ao girar o dispositivo.

 [3] Com a presença da ViewModel, podemos dividir as responsabilidades dos componentes da nossa
     aplicação. Uma vez que temos uma ViewModel, a nossa 'MuseumsActivity' já não está encarregada
     de fazer requisições para obter os dados junto ao banco de dados para serem mostrados,
     pois, agora a ViewModel é que tem essa responsabilidade.

     Sendo assim, muito código que antes era utilizado para fazer requisições e/ou persistir
     dados e que ficava ocupando várias linhas da nossa classe 'MuseumsActivity' agora irá ficar
     em 'MuseumViewModel'. E assim, teremos classes mais curtas, simples e fáceis de rever ou
     revisar.

     Com esta divisão de responsabilidades fica mais fácil de testar ou realizar a depuração
     do código (debug) do nosso aplicativo em função de todas as partes no Android (Activities e
     Fragments) estarem separadas dos nossos dados. Ao falarmos em 'capacidade de testes' só
     isso já justifica o uso do ViewModel.

 [4] 'MuseumViewModel' recebe o repositório que é o responsável por interagir com o banco de
     dados representado pela classe 'MuseumRepository' num contexto em nível de aplicação que
     está em execução. No repositório acessamos a fonte de dados e de lá implementamos os
     callbacks (insert, update, delete) que a nossa fonte de dados deve executar.

 */

/**
 Classe 'MuseumViewModel'

 Utilizará o repositório representado pela classe 'MuseumRepository' e a classe 'LiveData' que
 terá armazenado na memória cache o resultado do método 'getAllMuseums()' presente na classe
 'MuseumRepository' que será retornado para a 'MuseumsActivity' através do método
 'getFullMuseums()' obtendo assim os seguintes benefícios para a aplicação (ou aplicativo):

     - Podemos colocar um observador nos dados (em vez de pesquisar mudanças) e atualizar apenas
       a interface do usuário quando os dados realmente mudam.

     - O repositório é completamente separado da interface do usuário por meio da ViewModel.

 */

public class MuseumViewModel extends AndroidViewModel {

    private MuseumRepository mMuseumRepository;
    private LiveData<List<Museum>> mAllMuseums;

    public MuseumViewModel(Application application) {
        super(application);
        mMuseumRepository = new MuseumRepository(application);
        mAllMuseums = mMuseumRepository.getAllMuseums();
    }

    /**
     Métodos 'insert()' 'update()' 'delete()' 'deleteAll()' 'getFullMuseums()'

     Os 3 primeiros métodos tem como argumento o objeto 'museum' e irão sinalizar para o
     repositório o que deve ser feito junto ao banco de dados.

     O método 'deleteAll()' diz ao repositório para excluir fisicamente todas as linhas da
     entidade que representa a tabela no banco de dados.

     Já o método 'getFullMuseums()' retorna a lista completa (ou cheia) de museus que já estão
     presentes na entidade que representa a tabela de dados. Essa lista será observada na classe
     'MuseumsActivity' que é a responsável por apresentar os dados ao usuário (View).

     */

    public void insert(Museum museum){ mMuseumRepository.insert(museum);}

    public void update(Museum museum) { mMuseumRepository.update(museum);}

    public void delete(Museum museum) { mMuseumRepository.delete(museum);}

    public void deleteAll() { mMuseumRepository.deleteAll();}

    LiveData<List<Museum>> getFullMuseums() {return  mAllMuseums;}

}
