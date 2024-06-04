import {Injectable} from "@angular/core";
import {MessageService} from "primeng/api";

@Injectable({
  providedIn: 'root'
})
export class MessageCustomService {
  constructor(private messageService: MessageService) {
  }

  showSuccess(text: string) {
    this.messageService.add({severity: 'success', summary: 'Thông báo', detail: text});
  }

  showError(text: string) {
    this.messageService.add({severity: 'error', summary: 'Error', detail: text});
  }

  showWarn(text: string) {
    this.messageService.add({severity: 'warn', summary: 'Warn', detail: text});
  }

}
