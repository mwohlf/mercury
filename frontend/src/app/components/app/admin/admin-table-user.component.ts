import {Component} from '@angular/core';
import {AdminControllerService} from "../../../../generated/api/adminController.service";
import {UserDataSource} from "../../../models/user-data-source";


@Component({
    selector: 'admin-table-user',
    templateUrl: 'admin-table-user.component.html',
})

export class AdminTableUserComponent {

    userDataSource: UserDataSource;

    displayedColumns = ['uid', 'name'];

    constructor(private adminControllerService: AdminControllerService) {
        console.info("<constructor>")
        this.userDataSource = new UserDataSource(adminControllerService);
    }

}


