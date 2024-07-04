import {Component, HostListener} from '@angular/core';

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

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
    if (scrollPosition > 300) {
      const element = document.getElementById('chat-container');
      if (element) {
        element.style.bottom = '85px';
      }
    } else {
      const element = document.getElementById('chat-container');
      if (element) {
        element.style.bottom = '20px';
      }
    }
  }

}
