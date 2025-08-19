import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http'; // Importe o provideHttpClient

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  // Adicione provideHttpClient() dentro dos providers
  providers: [provideRouter(routes), provideHttpClient()]
}
