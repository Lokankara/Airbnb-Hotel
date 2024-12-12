import {Component, inject, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ButtonModule} from "primeng/button";
import {fontAwesomeIcons} from "./shared/font-awesome-icons";
import {FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {NavbarComponent} from "./layout/navbar/navbar.component";
import {FooterComponent} from "./layout/footer/footer.component";
import {ToastModule} from "primeng/toast";
import {ToastService} from "./layout/toast.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ButtonModule, FaIconComponent, NavbarComponent, FooterComponent, ToastModule],
  providers: [MessageService],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'airbnb-front';
  faIconLibrary = inject(FaIconLibrary);
  messageService = inject(MessageService);
  toastService = inject(ToastService);
  isListingView = true;

  ngOnInit(): void {
    this.initFont();
    this.listenToastService();
  }

  private initFont() {
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  private listenToastService() {
    this.toastService.sendSub.subscribe({
      next: newMessage => {
        if (newMessage && newMessage.summary !== this.toastService.INIT_STATE) {
          this.messageService.add(newMessage);
        }
      }
    });
  }
}
