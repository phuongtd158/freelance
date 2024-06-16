import {Component} from '@angular/core';

@Component({
  selector: 'app-bubble',
  templateUrl: './bubble.component.html',
  styleUrls: ['./bubble.component.css']
})
export class BubbleComponent {

  showContact: boolean = true

  handleClick() {
    this.showContact = !this.showContact
    const chatOptions: any = document.getElementById('chat-options');
    const chatButton: any = document.getElementById('chat-button');

    if (chatOptions.style.display === 'flex') {
      chatOptions.style.display = 'none';
    } else {
      chatButton.classList.add('shake');

      chatButton.addEventListener('animationend', function () {
        chatButton.classList.remove('shake');
      });
      chatOptions.style.display = 'flex';
    }
  }

}
