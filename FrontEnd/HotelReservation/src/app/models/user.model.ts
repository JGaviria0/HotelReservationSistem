export class UserModel {
    document: number;
    name: string;
    phone: string; 
    documentType: string; 
    userType: string;
    password: string;

    constructor() {
        this.document = 0;
        this.documentType = '';
        this.name = '';
        this.userType = '';
        this.phone = '';
        this.password = '';
    }
}