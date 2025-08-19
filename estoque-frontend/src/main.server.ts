// Em: src/main.server.ts

import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app'; // <-- CORREÇÃO: O nome correto é AppComponent
import { config } from './app/app.config.server';

const bootstrap = () => bootstrapApplication(AppComponent, config); // <-- CORREÇÃO: Usar AppComponent aqui

export default bootstrap;
