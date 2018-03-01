import {Component} from '@angular/core';
import {AlertService} from '../../services/alert.service';


@Component({
    template: ''
    + '<p>Label</p><br/>'
    + '<a (click)="doAlert1()"> show alert </a><br/>'
    + '<a (click)="doAlert2()"> show closeable alert </a><br/>'
    + '<a (click)="doAlert3()"> show transient alert </a><br/>'
    + '<a (click)="doAlert4()"> show timeout alert </a><br/>'
    + '<br/>'
})

export class LabelComponent {

    title: 'Label Component...';


    constructor(private alertService: AlertService) {

    }

    public doAlert1() {
        this.alertService.error("another alert")
            .show();
    }

    public doAlert2() {
        this.alertService.message("this is a closeable alert <br/> with a newline")
            .closeable()
            .show();
    }

    public doAlert3() {
        this.alertService.error("this is a transient alert")
            .transient()
            .show();
    }

    public doAlert4() {
        this.alertService.message("this is a timeout alert")
            .timeout(5)
            .show();
    }


}
