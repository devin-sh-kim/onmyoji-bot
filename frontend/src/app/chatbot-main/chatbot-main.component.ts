import { Component, OnInit } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

class KakaoResponse {
  message: Message;
}

class Message {
  text: string;
  date: Date;
}

@Component({
  selector: 'app-chatbot-main',
  templateUrl: './chatbot-main.component.html',
  styleUrls: ['./chatbot-main.component.css']
})
export class ChatbotMainComponent implements OnInit {

  inputMessage = '우산요괴';

  messages: Message[] = [];

  constructor(private http: Http) { }

  ngOnInit() {
  }

  onClickSend() {
    console.log(this.inputMessage);

    this.sendMessage(this.inputMessage);

  }

  sendMessage(text) {

    this.getMessage(text).then(message => {
      message.date = new Date();
      this.messages.push(message);
    });

  }

  private getMessage(text): Promise<Message> {

    const url = '/onmyoji-bot/kakao/message';

    const data = {
      user_key: 'encryptedUserKey',
      type: 'text',
      content: text
    };


    return this.http.post(url, data)
      .toPromise()
      .then(response => response.json().message as Message)
      .catch(this.handleError);

  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
