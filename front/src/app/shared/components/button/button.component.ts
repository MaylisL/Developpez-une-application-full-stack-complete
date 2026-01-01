import { Component, Input} from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent {

  @Input() label!: string;
  @Input() type: 'button' | 'submit' = 'button';
  @Input() disabled = false;
  @Input() icon?: string;
  @Input() routerLink?: string;
  @Input() variant: 'primary' | 'outline' = 'primary';

}
