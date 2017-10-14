import {Component} from '@angular/core';
import {UserControllerService} from "../../../generated/api/userController.service";
import {UserDataSource} from "../../models/user-data-source";


@Component({
    selector: 'admin-table-user',
    templateUrl: 'admin-table-user.component.html',
})

export class AdminTableUserComponent {

    userDataSource: UserDataSource;

    displayedColumns = ['uid', 'name'];

    constructor(private adminControllerService: UserControllerService) {
        this.userDataSource = new UserDataSource(adminControllerService);
    }

}


