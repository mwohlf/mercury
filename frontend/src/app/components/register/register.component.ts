import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';


@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

    public registrationForm: FormGroup;

    ngOnInit(): void {
        this.createForm();
    }


    private createForm() {
        this.registrationForm = new FormGroup({

        });
    }

    public login() {
    }

}
