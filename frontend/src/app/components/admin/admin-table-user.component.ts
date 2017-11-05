import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserDataSource} from "../../models/user-data-source";
import {User} from "../../../generated/model/user";


@Component({
    selector: 'admin-table-user',
    templateUrl: 'admin-table-user.component.html',
})

export class AdminTableUserComponent implements OnInit, OnDestroy {

    displayedColumns = ['uid', 'name', 'email', 'action'];

    constructor(public userDataSource: UserDataSource) {
    }

    ngOnInit(): void {
        this.userDataSource.refresh()
    }

    ngOnDestroy(): void {

    }

    onEdit(user: User) {

    }

    onDelete(user: User) {

    }

    onDisable(user: User) {

    }

    onEnable(user: User) {

    }

}


