import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserDataSource} from '../../models/user-data-source';
import {User} from '../../../generated/model/user';


@Component({
    selector: 'admin-user-list',
    templateUrl: 'admin-user-list.component.html',
})

export class AdminUserListComponent implements OnInit, OnDestroy {

    displayedColumns = ['uid', 'name', 'email', 'action'];

    constructor(public userDataSource: UserDataSource) {
    }

    ngOnInit(): void {
        this.userDataSource.refresh()
    }

    ngOnDestroy(): void {
    }

    onDelete(user: User) {
    }

    onDisable(user: User) {
    }

    onEnable(user: User) {
    }

}


