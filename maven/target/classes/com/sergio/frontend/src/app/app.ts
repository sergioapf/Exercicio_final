import { Component, inject } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  template: `
  <h1>Calculadora Labseq</h1>
    <input type="number" [(ngModel)]="n" min="0" placeholder="Digite um número inteiro não-negativo" />
    <button (click)="consultar()">Submeter</button>

    <div *ngIf="resultado !== null">
      <p style="white-space: normal; word-wrap: break-word; overflow-wrap: break-word;">
      l({{ n }}) = {{ resultado }}</p>
    </div>
    <p *ngIf="error" style="color:red">{{ erro }}</p>
`
})
export class App {
  n: number | null = null;
  resultado: string | null = null;
  erro = '';

  private http = inject(HttpClient);

  consultar() {
    this.erro = '';
    this.resultado = null;

    if (this.n === null || this.n < 0) {
      this.erro = 'Por favor, digite um número inteiro não-negativo.';
      return;
    }

    this.http.get(`http://localhost:8080/labseq/${this.n}`, { responseType: 'text' })
      .subscribe({
        next: data => {
          console.log('Resposta da API:', data);
          this.resultado = data;
        },
        error: () => {
          this.erro = 'Erro ao consultar a API.';
        }
      });
  }
}
