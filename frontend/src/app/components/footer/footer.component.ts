import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
    selector: 'mrc-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss']

})
export class FooterComponent implements OnInit {

    private idString: string;

    constructor(private authService: AuthService) {

    }

    ngOnInit() {
        this.idString = " " + this.authService.getUsername() + "/" + this.authService.getUserId();
    }

}
