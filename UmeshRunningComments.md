1. Assume single User and multiple files mode
2. Git API
    1. Get a list of user files
    1. Get a list of version ids for a user file
    1. Get content for a specified version id.
    2. Get 2 Consecutive Git versions of the same file:
            (older version id will be called the left, newer version id will be called the right version id)
            input => if null get the two latest versions
                     if a single version id then
                        if left version id then get {older than left version id+content, left version id+content}
                        if right version id then get {right version id+content, newer than right version id + content}
            output => {older version id, older version content, newer version id, newer content}
            next cycle => If looking for the next
    3. Get latest version of git file(version id, content)
    4. Get the previous version id content for a specified version id
    5. Get Next version id content for a specified version id
