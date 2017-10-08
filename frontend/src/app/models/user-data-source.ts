import {User} from "../../generated/model/user";
import {AdminControllerService} from "../../generated/api/adminController.service";
import {Observable} from "rxjs/Observable";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";

export class UserDataSource extends DataSource<User> {

    constructor(private adminControllerService: AdminControllerService) {
        super();
        console.info("<constructor> ")
    }

    /** Connect function called by the table to retrieve one stream containing the data to render. */
    connect(collectionViewer: CollectionViewer): Observable<User[]> {
        console.info("<connect> adminControllerService: '" + this.adminControllerService + "'")

        return this.adminControllerService.findPageUsingGET().map(response => response.content);
    }

    disconnect(collectionViewer: CollectionViewer) {}

}