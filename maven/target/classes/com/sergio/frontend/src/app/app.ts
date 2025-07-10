import { Component, inject } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  template: `
  <div style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100vh; font-size: 1.5rem; text-align: center;">
    <h1 style="font-size: 2.5rem;">Calculadora Labseq</h1>

    <input
      type="number"
      [(ngModel)]="n"
      min="0"
      placeholder="Digite um número inteiro não-negativo"
      style="padding: 12px 20px; font-size: 1.2rem; border: 1px solid #ccc; border-radius: 8px; width: 300px; margin: 10px 0;"
    />

    <button
      (click)="consultar()"
      style="padding: 12px 24px; font-size: 1.2rem; border: none; border-radius: 8px; background-color: #007bff; color: white; cursor: pointer;"
    >
      Submeter
    </button>

    <div *ngIf="resultado !== null" style="margin-top: 20px;">
      <p style="white-space: normal; word-wrap: break-word; overflow-wrap: break-word;">
        l({{ n }}) = {{ resultado }}
      </p>
    </div>

    <p *ngIf="erro" style="color:red; margin-top: 15px;">{{ erro }}</p>
  </div>
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
