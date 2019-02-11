export class PaperQuery {

    constructor(query, phrase, operator, field) {
        this.query = query;
        this.phrase = phrase;
        this.operator = operator;
        this.field = field;
    }

    query: string;
    phrase: boolean;
    operator: string;
    field: string;
}