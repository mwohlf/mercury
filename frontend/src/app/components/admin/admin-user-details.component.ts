import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserDataSource} from '../../models/user-data-source';
import {Subject} from '../../../generated/model/subject';
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

    onEdit(user: Subject) {

    }

    onDelete(user: Subject) {

    }

    onDisable(user: Subject) {

    }

    onEnable(user: Subject) {

    }

}


