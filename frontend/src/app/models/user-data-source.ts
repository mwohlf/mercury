import {Subject} from '../../generated/model/subject';
import {UserControllerService} from '../../generated/api/userController.service';
import {Observable} from 'rxjs/Observable';
import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {AlertService} from '../services/alert.service';
import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class UserDataSource extends DataSource<Subject> {

    private subject = new BehaviorSubject<Subject[]>([]); // start with null subject


    // Observable<User[]> content: = Observable.create();

    // TODO: implement a proper observable here that send array of userdata wheneveer filter, page, sort changes in the frontend grid...

    constructor(private adminControllerService: UserControllerService,
                private alertService: AlertService) {
        super();
    }

    // see: https://github.com/angular/material2/issues/5917
    /** Connect function called by the table to retrieve one stream containing the data to render. */
    connect(collectionViewer: CollectionViewer): Observable<Subject[]> {
        return this.subject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer) {

    }

    public refresh(): void {
        this.adminControllerService.findPageUsingGET().subscribe(
            result => {
                this.subject.next(result.content);
            },
            error => {
                this.alertService.handleError(error.error);
                this.subject.next([]);
            }
        );
    }

}
