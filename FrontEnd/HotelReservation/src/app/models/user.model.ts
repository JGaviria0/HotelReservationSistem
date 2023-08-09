export class UserModel {
    document: string;
    name: string;
    phone: string; 
    documentType: string; 
    userType: string;
    password: string;

    constructor() {
        this.document = '';
        this.documentType = '';
        this.name = '';
        this.userType = '';
        this.phone = '';
        this.password = '';
    }
}