import { Component, ChangeDetectorRef } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../Services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
    errorMessage: string = '';
    userName: string = "";
    password: string = "";
    formData!: FormGroup;
        constructor(private authService : AuthService, private router : Router, private cdr: ChangeDetectorRef) { }

    ngOnInit() {
      this.formData = new FormGroup({
         userName: new FormControl("admin"),
         password: new FormControl("admin"),
      });
    }

    onClickSubmit(userName?: string, password?: string) {
      this.userName = userName ?? this.userName;
      this.password = password ?? this.password;

      console.log('Login page: ' + this.userName);
      console.log('Login page: ' + this.password);

      this.errorMessage = '';
      this.authService.login(this.userName, this.password)
        .subscribe((res) => {
          console.log('Login result:', res);
          if (res.success) {
            this.router.navigate(['/dashboard']);
          } else {
            // ensure UI updates reliably after async response
            this.errorMessage = res.message ?? 'Login failed: invalid credentials';
            this.cdr.detectChanges();
          }
        });
    }


}
