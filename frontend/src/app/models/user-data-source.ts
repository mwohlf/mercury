import {User} from "../../generated/model/user";
import {UserControllerService} from "../../generated/api/userController.service";
import {Observable} from "rxjs/Observable";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";

export class UserDataSource extends DataSource<User> {

    // Observable<User[]> content: = Observable.create();

    // TODO: implement a proper observable here that send array of userdata wheneveer filter, page, sort changes in the frontend grid...

    constructor(private adminControllerService: UserControllerService) {
        super();
    }

    /** Connect function called by the table to retrieve one stream containing the data to render. */
    connect(collectionViewer: CollectionViewer): Observable<User[]> {
        return this.adminControllerService.findPageUsingGET().map(response => response.content);
    }

    disconnect(collectionViewer: CollectionViewer) {

    }

}