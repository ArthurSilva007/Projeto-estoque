// Em: src/main.ts

import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app'; // <-- CORREÇÃO: O nome correto é AppComponent

bootstrapApplication(AppComponent, appConfig) // <-- CORREÇÃO: Usar AppComponent aqui
  .catch((err) => console.error(err));
