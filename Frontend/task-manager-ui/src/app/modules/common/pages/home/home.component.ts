import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

import { modules } from '../../data/module-definitions.data';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.component.html',
})
export class HomeComponent {
  readonly modules = modules;
}
