import {Component} from '@angular/core';
import {UserDataSource} from "../../models/user-data-source";


@Component({
    selector: 'admin-table-user',
    templateUrl: 'admin-table-user.component.html',
})

export class AdminTableUserComponent {

    displayedColumns = ['uid', 'name'];

    constructor(public userDataSource: UserDataSource) {
    }

}


