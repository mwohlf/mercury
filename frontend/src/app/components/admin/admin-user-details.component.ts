import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserDataSource} from '../../models/user-data-source';
import {User} from '../../../generated/model/user';
import {ActivatedRoute} from "@angular/router";


@Component({
    selector: 'admin-user-details',
    templateUrl: 'admin-user-details.component.html',
})

export class AdminUserDetailsComponent implements OnInit, OnDestroy {

    displayedColumns = ['uid', 'name', 'email', 'action'];
    private uid: any;

    constructor(private route: ActivatedRoute,
                public userDataSource: UserDataSource) {
    }

    ngOnInit(): void {
        this.uid = this.route.snapshot.params['uid'];
        // this.userDataSource.
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


