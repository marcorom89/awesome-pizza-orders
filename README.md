# Test Project with SpringBoot3 + Java17 + Serverless Framework v4

### Traccia esercizio con commenti sulla realizzazione:

* Come pizzeria "Awesome Pizza" voglio creare il mio nuovo portale per gestire gli ordini dei miei clienti.
    * Si ipotizza che l’utente tramite l’interfaccia grafica vede una lista di prodotti, organizzati per categorie, con nome descrizione, immagine prezzo ecc
    * Per semplificare la lista di prodotti non verrà gestita in questa fase
    * La creazione dell’ordine POST /orders riceve in input i dati inseriti dall’utente. 
    * Questi possono essere un nominativo dell’ordinante e l’indirizzo email (per inviare successivamente le notifiche quindi sono opzionali e inseriti anche dopo con la modifica)
    * I dati obbligatori sono una lista di prodotti, con codice prodotto/nome e quantità e opzionalmente delle note (per indicare eventuali modifiche)

* Il portale non richiede la registrazione dell'utente per poter ordinare le sue pizze. 
    * Quindi non è necessaria autenticazione utente sulle API, ma nel caso è possibile prevedere protezione degli endpoint con API KEY (direttamente con API Gateway)

* Il pizzaiolo vede la coda degli ordini e li può prendere in carico uno alla volta. 
    * la lista degli ordini deve essere ordinata dal più vecchio al più recente, con possibilità di filtro sullo stato in modo da scegliere di non visualizzare gli ordini completati. 
    * Pertanto sull’endpoint GET /orders è necessario prevedere ordinamento per data di creazione, filtro sullo stato (opzionalmente è possibile aggiungere il limite sulla risposta per ottenere solo l'ultimo ordine)

* Quando la pizza è pronta, il pizzaiolo passa all'ordine successivo. 
    * Aggiornamento dello stato dell’ordine. Più in generale si possono aggiornare tutte le informazioni dell’ordine, tranne la data di creazione, di aggiornamento e l’ID

* L’utente riceve il suo codice d'ordine e può seguire lo stato dell'ordine fino all'evasione. 
    * La creazione dell’ordine restituisce tutto l’oggetto creato, tra cui anche l’ID dell’ordine. Di conseguenza questo può essere usato sulla GET /orders/{id} per leggerne lo stato
    * Per semplicità il codice ordine è l’ID dell’ordine, anche se risulta di difficile lettura

Come team, procediamo allo sviluppo per iterazioni. 
Decidiamo che nella prima iterazione non sarà disponibile un'interfaccia grafica, ma verranno create delle API al fine di ordinare le pizze e aggiornarne lo stato. 

Decidiamo di utilizzare il framework Spring e Java (versione 17 o superiore). 
Decidiamo di progettare anche i test di unità sul codice oggetto di sviluppo.

Considerazione sul database:
* Utilizzo di DynamoDB per memorizzare gli ordini
* l’HASH key è l’ID dell’ordine, mentre non è presente una RANGE key
* Utilizzo di un indice globale secondario per la visualizzazione di tutti gli ordini. La HASH key è l’entityType che sarà “Order”, mentre la RANGE key è createdAt, in modo da permettere l’ordinamento

____

### Tecnologie Utilizzate

Per la realizzazione delle API è stato utilizzato SpringBoot v3 con Java17. Per la creazione dell'ambiente di esecuzione delle API è stato utilizzato Serverless Framework v4 con provider AWS. Il progetto utilizza pertanto:
* **API Gateway** v1 per esporre l'API
* **Lambda Funtion** come ambiente per l'ezecuzione delle API
* **DynamoDB** per la memorizzazione dei dati degli ordini

Per rendere compatibile SpringBoot con AWS Lambda è stata utilizzata la libreria [Serverless Java Container](https://github.com/aws/serverless-java-container/wiki/Quick-start---Spring-Boot3), che vede l'utilizzo di una classe handler **StreamLambdaHandler**, la quale riceve l'evento da API Gateway e lo converte in una richiesta facilmente interpretabile da Spring

### Setup dell'ambiente e Deploy

Per installare Serverless framework e tutte le dipendenze è necessario avere installato nodejs v20+ ed npm v10+ e configurare le credenziali AWS. Configurare il file `.env` come da esempio.

In seguito sarà sufficiente eseguire il comando

```bash
npm install
```

Per eseguire correttamente il deploy è necessario effettuare il build del progetto tramite il comando

```bash
./gradlew buildZip --stacktrace
```

e in seguito il comando per il deploy

```bash
npx sls deploy --stage test --verbose
```

Per fini dimostrativi sono disponibili i seguenti endpoint già pubblicati che possono essere chiamati per eseguire un test delle funzionalità

* GET -> https://fk5kn6ss73.execute-api.eu-central-1.amazonaws.com/test/orders
* POST -> https://fk5kn6ss73.execute-api.eu-central-1.amazonaws.com/test/orders
* GET -> https://fk5kn6ss73.execute-api.eu-central-1.amazonaws.com/test/orders/{id}
* PATCH -> https://fk5kn6ss73.execute-api.eu-central-1.amazonaws.com/test/orders/{id}

### Unit Test

Sono stati realizzati 3 unit test per la classe OrderServiceImpl con JUnit e Mockito

Per eseguire i test è necessario eseguire il comando

```bash
./gradlew test --stacktrace
```